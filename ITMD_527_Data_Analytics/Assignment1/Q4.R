#Q4=====================================================


#1. Use caret library
library(caret)
library(MASS)

#Q2.Read the bhp.csv file
bhpModel= read.csv("bhp.csv", header = TRUE)
summary(bhpModel)
str(bhpModel)


#Q3. Convert outcome variable to a factor 0: failure; 1 ; Good
bhpModelClass = ifelse(modelY$CLASS == 1, "Good", "Failure")
str(bhpModelClass)
summary(bhpModelClass)
head(bhpModelClass)


#melt function
??melt
bhpModel.melt = melt(data = bhpModel, id.var = "CLASS")
summary(bhpModel.melt)
bhpModel.melt

# Plotting
library(reshape2)
library(ggplot2)

ggplot(data = bhpModel.melt , aes(x= CLASS, y=value)) + geom_boxplot()+ facet_wrap(~ variable, ncol = 3)
 
#working add in azure
library(corrplot)
bh = cor(bhpModel[1:7])
corrplot.mixed(bh)




#Q4.Create 70:30 training / test data set

library(partykit)
library(grid)
library(mlbench)
library(pROC)

set.seed(1511) # create random numbers
ind = sample(2, nrow(bhpModel), replace = TRUE, prob=c(0.7, 0.3))

train = bhpModel[ind == 1, ] 
# training dataset
test = bhpModel[ind == 2, ] 
# test dataset
str(train)


# look how many are good and how many are failure in each train and test dataset
table(train$CLASS) 
table(test$CLASS)



#Q5.Build a logistic regression model and store it in logit variable
logit = glm(CLASS ~., data=train ,family="binomial")
summary(logit)

#logit = glm(CLASS ~.-TAX, data=train ,family="binomial")
#summary(logit)

#logit = glm(CLASS ~.-B, data=train ,family="binomial")
#summary(logit)

# As per the summary, I could see that variable TAX  was not significant(looking at p-value) and variable B was less significant
#so I tried removing these variables from the model, but the AIC and residual error increased which should be low in a model. So 
#we could come up with a better model when we included both the variables in the model.


#Q6.Discuss the deviance results and significance of coefficients
exp(coef(logit))

# Looking at the summary of logit model and running the above command, I can say that all the coefficients are significant based 
#on the P-value.But variables RAD and B can contribute more
#towards predicting the resluts as compared to other variables.


#Q7.Compute probabilities of success using train data and store it in a variable
trainPredict = predict (logit, train, type="response")
trainPredict[1:7]


  
#Q8.Classify the cases using a cutoff probability of .5
trainCutOff  = ifelse(trainPredict > 0.5 , 1 , 0)

#Q9.Generate the error/classification-confusion matrix 
confusionMatrixTrain = table(Predicted = trainCutOff,Actual = train$CLASS  )
confusionMatrixTrain

#FALSE POSITIVE - 14
#FALSE NEGATIVE - 15
#TRUE POSITIVE - 120
#TRUE NEGATIVE - 115

# there are 107 variables which did not contribute to predict the class according to the predicted and observed model
# similarly there were 112 variables which contributed to predict the CLASS(this is the correct prediction, TRUE POSITIVE)
#according to the predicted and observed model.
# 15 and 27 are the misclassification errors(TRUE NEGATIVE) where the predicted and observed have different results.

misclassificationErrorTrain = 1-sum(diag(confusionMatrixTrain))/sum(confusionMatrixTrain)
misclassificationErrorTrain 
# The Error is just 10%, Here we can say that the model can predict more accurately.



#Q10.Repeat this for test data 
testPredict = predict (logit, test, type="response")
testPredict

testCutOff  = ifelse(testPredict > 0.5 , 1 , 0)

confusionMatrixTest = table(Predicted = testCutOff, Actual = test$CLASS  )
confusionMatrixTest

misclassificationErrorTest = 1-sum(diag(confusionMatrixTest))/sum(confusionMatrixTest)
misclassificationErrorTest

#Used the same model(Logit) to run the test data and we can see the misclassificationError for Test data to be 18% which is due to the 
#less amount of data in the train dataset.



