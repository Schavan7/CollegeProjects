# FinalModels:
FinalModel =lm(median_house_value ~ latitude + median_income + ocean_proximity + housing_median_age + mean_rooms, data= train)
summary(FinalModel) # -- finalize
##pairs.panels(FinalModel)

FinalModelv2 =lm(median_house_value ~ households+ median_income + mean_bedrooms, data= train)
summary(FinalModelv2) # -- finalize

FinalModelv3 =lm(median_house_value ~ latitude +longitude+ housing_median_age + households+ median_income + mean_bedrooms, data=train)
summary(FinalModelv3)
vif(FinalModelv3)

FinalModelv4 =lm(median_house_value ~  housing_median_age + households+ median_income + mean_bedrooms, data=train)
summary(FinalModelv4)
plot(FinalModelv4)

coefficients(FinalModelv4)
confint(FinalModelv4)
#---------------------
FinalModelv4 =lm(median_house_value ~  housing_median_age + households+ median_income + mean_bedrooms, data=train)
summary(FinalModelv4)
plot(FinalModelv4)

#With interaction of colinera variables
FinalModelv5 =lm(median_house_value ~  housing_median_age + households+ median_income + mean_bedrooms+ (latitude * longitude), data=train)
summary(FinalModelv5)
#------------------------
anova()







#---------------------------Skip

#What each variable is contributing  in the Model
FinalModel =lm(median_house_value ~ latitude + median_income + ocean_proximity + housing_median_age , data= train)
summary(FinalModel)

TestModel1 = lm(median_house_value ~ latitude  + median_income + ocean_proximity, data= train)
summary(TestModel1) 
# By removing age from the model, the error increases and R squred decreases. 

TestModel2 = lm(median_house_value ~ latitude  + median_income + housing_median_age , data= train)
summary(TestModel2) 
# By removing ocean Proximity from the model, the error decreases and R squred and F-statistic increases slightly. 

TestModel3 =lm(median_house_value ~ latitude + ocean_proximity + housing_median_age, data= train)
summary(TestModel3)
# By removing median_income from the model, The error increases dramatically, R squred and F-statistic drops significantly.So we have to keep this 
# varibale in the model.

TestModel4 =lm(median_house_value ~ median_income + ocean_proximity + housing_median_age, data= train)
summary(TestModel4)
# By removing latitude from the model,the error increases and R squred and F-statistic also increases slightly. 

# We can come to a conclusion that all the varaibles contribute to make a good model and we have to keep them all in the model. 
#but the most significant one is the median_income.


#------ Testing the models.

#Using Trian dataset
#FinalTrain = lm(median_house_value ~ latitude + median_income + ocean_proximity + housing_median_age, data= train)
#summary(FinalTrain)
#confint(FinalTrain, conf.level = 0.95)

#TestModel = predict.lm(FinalTrain, data= test)
#summary(TestModel)





#________optional
actuals_preds <- data.frame(cbind(actuals=test$median_house_value, predicteds=prediction))  # make actuals_predicteds dataframe.
correlation_accuracy <- cor(actuals_preds) 
head(actuals_preds)

res <- stack(data.frame(Observed = test$median_house_value, Predicted = prediction))
res <- cbind(res, x = rep(dat$x, 2))
head(res)