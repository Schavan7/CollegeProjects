library(tidyverse)
library(reshape2)
library(Hmisc)
library(psych)
library(car)
library(alr3)
library(boot)
library(plyr)
library(dplyr)

# STEP 1: Importing the data

housing = read.csv("housing.csv", header = TRUE)
summary(housing)
str(housing)
#plot(housing)
# 207 NA's need to be addressed 
# Split the factor valriable(ocean_proximity column) to binary values to make it compatible with other variables.
# Converted the total_bedrooms and total_rooms into a mean_number_bedrooms and 
# mean_number_rooms columns for more accurate predictions

par(mfrow=c(1,1))



# ggplot to get a visual graph
ggplot(data = melt(housing), mapping = aes(x = value)) + 
geom_histogram(bins = 30) + facet_wrap(~variable, scales = 'free_x')
# Predicting the graph, we can see that:
#1. There are some old age homes included in the data, as there is a hike in at the right most corner.
#2. The median house value also has a blip at the corner,may be the houses are in the bay area, hence they are expensive
#3. We need to scale the data, as there are outliers in all areas.



#STEP 2 - CLEANING THE DATA
# 1. Insert missing values.
# The only column with missing values is "total_bedrooms", So imputing the median data 
# instead of mean  for NA's since median is less influenced by outliers.

housing$total_bedrooms[is.na(housing$total_bedrooms)] = median(housing$total_bedrooms , na.rm = TRUE)

#Cross verification:
str(housing)
summary(housing)



# 2. Convert total_rooms & total_bedrooms to mean of both the columns to scale the data.

housing$mean_bedrooms = housing$total_bedrooms/housing$households
housing$mean_rooms = housing$total_rooms/housing$households
str(housing)
drops = c('total_bedrooms', 'total_rooms')
housing = housing[ , !(names(housing) %in% drops)]
head(housing)


# 3. Converting the categorical variable into factors.
categories = unique(housing$ocean_proximity)
housing$ocean_proximity <- as.factor(revalue(housing$ocean_proximity, c("<1H OCEAN"="1", "INLAND"="2", "ISLAND"="3", "NEAR BAY"="4", "NEAR OCEAN"="5")))
housing$ocean_proximity




# 4.Scaling the numeric variable.
# we scale all the variables except the median_house_value and ocean_proximity as
# median house value will be used to predict the outcome and ocean_proximity is a factor variable.

drops = c('ocean_proximity','median_house_value')
housing_num =  housing[ , !(names(housing) %in% drops)]
head(housing_num)
scaled_housing_num = scale(housing_num)
head(scaled_housing_num)


#5. Merge the altered numerical and categorical dataframes

cleaned_housing = cbind(scaled_housing_num, median_house_value =housing$median_house_value, ocean_proximity =housing$ocean_proximity)
head(cleaned_housing)
summary(cleaned_housing)
#pairs(cleaned_housing)
#plot(cleaned_housing)


# STEP 3:  Create train and test data
#Created  70 / 30 train / Test data set 

set.seed(1000) 
ind = sample(2, nrow(cleaned_housing), replace = TRUE, prob=c(0.7, 0.3))
# training dataset
train = cleaned_housing[ind == 1, ] 
train = data.frame(train)
# test dataset
test = cleaned_housing[ind == 2, ] 
test = data.frame(test)

head(train)
head(test)
# To check if the partition is done correctly
nrow(train) + nrow(test) == nrow(cleaned_housing)



# CREATING THE MOEDLS :

#Corelation between variables:
#corelation between all variables to check if they are highly corelated between themselves.
cor(cleaned_housing)
cor.plot(cleaned_housing)

# longitude + latitude
# mean_bedrooms+ mean_rooms
# population + households
# all the above variables are highly corelated, so we have to select only one amongst them.
names(train)


#FULL MODEL:
cleaned_housing = data.frame(cleaned_housing)
fullModel = lm(median_house_value ~ latitude +longitude+ housing_median_age+ population + households+ median_income + mean_rooms + mean_bedrooms + ocean_proximity , data=train)
summary(fullModel)
coef(fullModel)
plot(fullModel)
# Even though the overall p-value is very significant, there are some variables which are not 
#significant, So tried and tested with every variable.



#REDUCED MODELS:
# DATA USED FOR ALL THE MODELS IS THE TRAIN DATA.
# mean_rooms variable removed: 
ReducedModel1 =lm(median_house_value ~ latitude +longitude+ housing_median_age+ population + households+ median_income + mean_bedrooms + ocean_proximity , data=train)
summary(ReducedModel1)
#We see that The ocean_proximity variavle is not significant, 
#even though all the others were significant


#ocean_proximity removed, since it was not significant in ReducedModel1
ReducedModel2 =lm(median_house_value ~ latitude +longitude+ housing_median_age+ population + households+ median_income + mean_bedrooms, data=train)
summary(ReducedModel2)
# In this Model I can see that all the variables are very significant to the model.
#So, I will check for multicolinearity within variables.


#Check correlation and Variation inflation factor(VIF): 
vif(ReducedModel2)
# longitude + latitude - Cannot be removed as they always go together
# population + households

# Population and household have high colinearity 
#so we can take only one variable out ie;household in ReducedModel3


#Check with households
ReducedModel3 =lm(median_house_value ~ latitude +longitude + housing_median_age + households+ median_income + mean_bedrooms, data=train)
summary(ReducedModel3) 
# All variables are very significant, R sqrure is better, 
#error is reduced, F statistic has alos improved.


# Below are just trials with other variable combination:
#replaced households with population 
ReducedModel4 = lm(median_house_value ~ latitude +longitude+ housing_median_age+ population + median_income + mean_bedrooms, data=train)
summary(ReducedModel4) 

# population is not very significant, go with households 
# untill now ReducedModel3 is more efficient.

# Removed latitude  and longitude:
ReducedModel5 =lm(median_house_value ~ housing_median_age + households + median_income + mean_bedrooms, data=train)
summary(ReducedModel5) 
# ReducedModel3 is still better.

#MODEL WITH INTERCATION TERMS:
#ReducedModel3 with Interaction Terms(latitude * longitude), latitude * longitude not significant:
ReducedModel6 =lm(median_house_value ~  housing_median_age + households+ median_income + mean_bedrooms + (latitude * longitude) , data=train)
summary(ReducedModel6)

# Use ReducedModel3 and age removed, not much if a difference,still ReducedModel3 is better.
ReducedModel7 =lm(median_house_value ~ latitude +longitude + households + median_income + mean_bedrooms, data=train)
summary(ReducedModel7)


#Compare  models using anova for cross verification:
anova(ReducedModel1, ReducedModel2,ReducedModel3,ReducedModel4,ReducedModel5,ReducedModel6)


#CONCLUSION: WITH ALL THE VARIABLES TRIED AND TESTED, I CONCLUDE THAT REDUCED MODEL 3 IS THE BEST ONE
# Here all variables are significant
# R-squared and Adjusted R-squared is the better than all the other variables
# F-statistic is good.
# Intercept is better than others. 

# Therefore  our finalModel is ReducedModel3 
FinalModel = ReducedModel3




#EVALUATION OF MODEL- Method 1
#Prediction on test dataset
prediction <- predict(FinalModel, test)
head(prediction)
head(test$median_house_value)
# WE CAN SEE THAT THE ACTUAL AND THE PREDICTED VALUES ARE PRETTY CLOSE TO EACH OTHER. 
# SO, WE CAN SAY WE HAVE A GOOD MODEL.


plot(test[,"median_house_value"], prediction,xlab = "actual",ylab = "predicted",main = "median_house_value")
abline(0,1, col="red")

#EVALUATION OF MODEL- Method 2
train$pred.price = predict(FinalModel, newdata= subset(train))
test$pred.price = predict(FinalModel,  newdata= subset(test))
summary(FinalModel)
train.corr = round(cor(train$pred.price, train$median_house_value), 2)
train.RSME = round(sqrt(mean((train$pred.price - train$median_house_value) ^ 2)))
train.MAE = round(mean(abs(train$pred.price - train$median_house_value)))
c(train.corr^2,train.RSME,train.MAE )

test.corr = round(cor(test$pred.price, test$median_house_value), 2)
test.RSME = round(sqrt(mean((test$pred.price - test$median_house_value) ^ 2)))
test.MAE = round(mean(abs(test$pred.price - test$median_house_value)))
c(test.corr^2,test.RSME,test.MAE )



#EVALUATION OF MODEL- Method 3
# Cross validation for generalized linear model
glm_house = glm(median_house_value ~ latitude +longitude + housing_median_age + households+ median_income + mean_bedrooms, data=train)
k_fold_cv_error = cv.glm(train , glm_house, K=5)
k_fold_cv_error$delta
glm_cv_rmse = sqrt(k_fold_cv_error$delta)[1]
glm_cv_rmse
glm_house$coefficients
# looking at the co-efficients I can say that median income had the
#biggest effect on predicting the house prices



