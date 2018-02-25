gasmf = read.csv("gasmf.csv", header = TRUE)
str(gasmf)
summary(gasmf)
gasmf$X=NULL 
# as the data was just confusing having X1(the ID column) and x1 the actual IV. I deleted the Id column to avoid confusion.



#1.
model =lm(y~x1+x6, data = gasmf)
summary(model)
abline(model)
plot(model)
par(mfrow=c(2,2))

#2
fullmodel = lm(y~. ,data=gasmf)
reducedModel = lm(y~x1+x6, data = gasmf)
anova(fullmodel, reducedModel)
anova(reducedModel)
names(gasmf)


#3.
summary(reducedModel)

#4
confint(reducedModel,"x1", level = 0.95)

#5
reducedModel = lm(y~x1+x6, data = gasmf)
summary(reducedModel)
# I can reject null hypothesis for x1 and i cannot reject null hypothesis 
#for x6 as the p value is greater than .05

gasmf$x1
x1p = lm(y~x1, gasmf)
summary(x1p)

gasmf$x6
x6p = lm(y~x6, gasmf)
summary(x6p)

#6 ci for 95% == NOT SURE ABT THIS
predict(reducedModel, data.frame(x1=275, x6=2), interval="confidence",level= 0.95)


#7
predict(reducedModel, data.frame(x1=257,x6=2), interval="confidence",level= 0.95)

#8
reducedModelWithX = lm(y~x1, data = gasmf)
predict(reducedModelWithX, data.frame(x1=257), interval="confidence",level= 0.95)
#campare the two models from 7 and 8
#9 From question 7 and 8 we get different CIs and we can say that removing x6 will 
#deccrease the CI of the curve whcih is good for the regression.







