#Q3
#1.
housePrice = read.csv("houseprice.csv", sep = ";", header = TRUE)
summary(housePrice)
names(housePrice)
summary(housePrice) 
str(housePrice)
names(housePrice) = c("sales price/10000","taxes", "number of baths", "lot size/1000 sq.ft", "living space/1000sqft", "no.ofgaragestalls","number of rooms","no of bed rooms","age of home", "no. of fire places")
names(housePrice)

#1
housePriceModel = lm(y~ x1+x2+x3+x4+x5+x6+x7+x8+x9, data=housePrice)
summary(housePriceModel)
str(housePriceModel)

# give the excel or R summary where the individual P and T values are refected:
#2.From the summary above I can say that, when we plot all the variables agaist the dependent variable, We can see that
# even though we get higher P-values for most of the variables, the overall regression is very significant as per the overall p-value.
summary(housePriceModel)



#3 give the excel or R summary where the individual P and T values are refected:
# Looking at the individual P values I can say that variables(x1, x2, x5, and x9) are more signfincant
#as compared to rest of the variables since they have the p-vales less than 0.5 and can be considered as
# good regressors for the model.

t.test(x1,data=housePrice)
t.test(x2,data=housePrice)
t.test(x3,data=housePrice)
t.test(x4,data=housePrice)
t.test(x5,data=housePrice)
t.test(x6,data=housePrice)
t.test(x7,data=housePrice)
t.test(x8,data=housePrice)
t.test(x9,data=housePrice)

#individual model
hpmodel1 = lm(y~x1, houseprice)
summary(hpmodel1) # significant

hpmodel2 = lm(y~x2, houseprice)
summary(hpmodel2)



# 4
library(reshape)
library(car)
library(ggplot2)
# all regressors included:
housePriceModel = lm(y~ x1+x2+x3+x4+x5+x6+x7+x8+x9, data=housePrice)
summary(housePriceModel) # overall p value 0.000185.

# with x3 excluded
ModelWOx3 = lm(y~ x1+x2+x4+x5+x6+x7+x8+x9, data=housePrice)
summary(ModelWOx3) # p-value: 5.516e-05

# with x3 excluded
ModelWOx4 = lm(y~ x1+x2+x3+x5+x6+x7+x8+x9, data=housePrice)
summary(ModelWOx4) # p-value: 6.39e-05

# with x3 and x4 excluded
ModelWOx3x4 = lm(y~ x1+x2+x5+x6+x7+x8+x9, data=housePrice)
summary(ModelWOx3x4) #  p-value: 1.909e-05






#5
vif(lm(y~ x1+x2+x3+x4+x5+x6+x7+x8+x9, data=housePrice))
vif(housePriceModel)
# by loking at the above result we can say that there is a problem of co-linearity in this model


#corelation between all variables
cor(housePrice)

# High colreration variables found in this model:
#x1, x4   x7,x6 (higlight them to make it visible)

x1x4Model = lm(y~ x1+x4, data=housePrice)
summary(x1x4Model)
vif(x1x4Model)

x7x6Model = lm(y~ x7+x6, data=housePrice)
summary(x7x6Model)
vif(x7x6Model)


# we can also use the below method to calculate the co-relation
cor(x1, x4, method ="pearson")
cor(x6, x7)

#After running the above command we can see in the console that when we plot one variable with itself it has the colinearity
#of 1. Here we have to look for the values which are close to 1 when plotted against each other. so aftre careful observation
# we can see that variable x1 when plotted against x4 and variable x7 when plotted against x6 have high colinearity.
#so we can say that their is potential problems of colinearity in this model and we can only select only one of the variable 
# ie: either x1 or x4 (not both). same applies to x7 and x6, only one can be selected and not both.



