

#Q1.
#1.
gasmf = read.csv("nfl1.csv", header = TRUE)
str(nfl1)
summary(nfl1)
str(nfl1)
head(nfl1)
plot(nfl1)
hist(nfl1)
nfl1[1, 2]
names(nfl1)
class(nfl1)
nfl1=data.frame(nfl1)
str(nfl1)
summary(nfl1)
plot(nfl1)
plot(nfl1$x1)
plot(nfl1$x2)
plot(nfl1$x3)
plot(nfl1$x4)
plot(nfl1$x5)
plot(nfl1$x6)
plot(nfl1$x7)
plot(nfl1$x8)
plot(nfl1$x9)
attach(nfl1)
y=1:28
y
x= nfl1$x1

abline(lm(y~x))
lm(y~x1)
abline(lm(y~x1))
names(nfl1)
library(Hmisc)

pairs(nfl1)
result = lm(y ~ x2+x7+x8, data=nfl1)
summary(result)


# plots for all varibales:
pairs(nfl1)
result = lm(y ~ x2+x7+x8, data=nfl1)
summary(result)


model1= lm(y~x1 , data=nfl1)
model1
summary(model1)
plot(model1)

#abline(model1)

model2= lm(y~x2 , data=nfl1)
model2
summary(model2)
plot(model2)

model3= lm(y~x3 , data=nfl1)
model3
summary(model3)
plot(model3)


model4 =lm(y~x4 , data=nfl1)
model4
summary(model4)
plot(model4)


model5 =lm(y~x5 , data=nfl1)
model5
summary(model5)
plot(model5)

model6 = lm(y~x6 ,data=nfl1)
model6
summary(model6)
plot(model6)

model7 = lm(y~x7 ,data=nfl1)
model7
summary(model7)
plot(model7)

model8 = lm(y~x8 ,data=nfl1)
model8
summary(model8)
plot(model8)

model9 = lm(y~x9 ,data=nfl1)
model9
summary(model9)
plot(model9)


plot(model1)
plot(model2)
plot(model3)
plot(model4)
plot(model5)
plot(model6)
plot(model7)
plot(model8)
plot(model9)

modelx1x2 = lm(y~x1*x2, data=nfl1)
plot(modelx1x2)
summary(modelx1x2)
termplot(modelx1x2)



#2. Analysis of variance using the 3 most relevant IV.
attach(nfl1)
combined_data = data.frame(cbind(x2,x7,x8))
str(combined_data)
stack_data = stack(combined_data)
stack_data
Anova_results = aov(values~ind, data=stack_data)
Anova_results
summary(Anova_results)
#RESULT ==  F(2, 81) = 417, p<2e-16(***)


fullmodel = lm(y~x1+x2+x3+x4+x5+x6+x7+x8+x9 , nfl1)
reducedModel = lm(y~x2+x7+x8, nfl1)
anova(fullmodel, reducedModel)
#anova(reducedModel)



combined_data = data.frame(cbind(fullmodel,reducedModel))
str(combined_data)
stack_data = stack(combined_data)
stack_data
Anova_results = aov(values~ind, data=stack_data)
Anova_results
summary(Anova_results)


#3.
help(t.test)
t.test(y,x8,paired=TRUE)
help("var.test")

fullmodel= lm (y~ x1 + x7 + x8)
summary(fullmodel)

reduceModelX1X7 = lm (y~ x1 + x7)
summary(reduceModelX1X7)


reduceModelX1X8 = lm (y~ x1 + x8)
summary(reduceModelX1X8)

reduceModelX7X8 = lm (y~ x7 + x8)
summary(reduceModelX7X8)

#new analysis
reducedModel = lm(y~x2+x7+x8, nfl1)
#compared to the above model
reducedModel = lm(y~x2+x7+x9, nfl1)
summary(reducedModel)
#conclusion: we can say looking at the p value and the t value that all the variables are higjl significant

#4

allVariables = lm(y~ x2+x7+x8, data=nfl1)
summary(allVariables)

varx2x8= lm(y~ x2+x8, data=nfl1)
summary(varx2x8)

varx2x7= lm(y~ x2+x7, data=nfl1)
summary(varx2x7)

varx7x8 = lm(y~ x7+x8, data=nfl1)
summary(varx7x8)

#x2 most significant, x7 is the least significant model, x8 has the second highest role (p-value).


#5.Ci of 95%
confint(allVariables,"x2", level = 0.95)
confint(allVariables,"x7", level = 0.95)
confint(allVariables,"x8", level = 0.95)

# when we summary our full model(x2+x7+x8) we can observe that all the variable fall under the 95% confidence interval


#6
predict(allVariables, data.frame(x2=2127, x8=2110, x7 = 58), interval="confidence",level= 0.95)
# so we get the y value and the 95% Ci for the value above.

#7
modelx7x8 = lm(y ~ x7+x8, nfl1)
summary(modelx7x8)
predict(modelx7x8, data.frame( x8=2110, x7=58), interval="confidence",level= 0.95)
#Campare it with predict(allVariables, data.frame(x2=2127, x8=2110, x7 = 58), interval="confidence",level= 0.95)

#When we compare the two models we can say that x2 plays am important role, as when we exclude the x2 variable, the CI range increases 
#which is not more significant as compared to including x2 which has a narrower range.


#8
model1 = lm(y~ x2+x7+x8, nfl1)
summary(model1)
model2 = lm(y~ x7+x8, nfl1)
summary(model2)

#After looking at the summary for the r squared value  of the two models we can say that x2 is a valuable contributer as it contributed to explain the 
#variantion in the Y more when included with other variables as compared to when not included.


#9- Not very sure
confint(allVariables,"x7", level = 0.95)
modelx7x8 = lm(y~ x7+x8, nfl1)
summary(modelx7x8)
predict(modelx7x8, data.frame( x8=2110, x7=58), interval="confidence",level= 0.95)
predict(modelx7x8, data.frame( x8=2100, x7=56.0), interval="confidence",level= 0.95)
# by changing the values of x7 and x8 we can say that the CI interval increases which says that we are less confident.

#10 -- we can say that that we have selected the best three regressors for the model and ommitted the rest based on the significance
#level. We also tend to see the importance of all the three variables as removing one of the regressor will increase the CI hence decreases the confidence
# So having all the variables can give us the best regresssion model.




