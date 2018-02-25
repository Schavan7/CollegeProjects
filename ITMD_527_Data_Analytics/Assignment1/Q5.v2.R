#Q1.	Load the following packages  -
library(class) #k-nearest neighbors 
library(kknn) #weighted k-nearest neighbors 
library(e1071) #SVM 
library(caret) #select tuning parameters 
library(MASS) # contains the data 
library(reshape2) #assist in creating boxplots 
library(ggplot2) #create boxplots 
library(kernlab) #assist with SVM feature selection


#Q2.Combine  following data sets - Pima.tr and Pima.te
??pima.te
??pima.tr
FinalData = rbind(Pima.tr, Pima.te)
summary(FinalData)
str(FinalData)
View(FinalData)



#Q3. Use the melt function by "type"
FinalData.melt = melt(FinalData , id.var = "type")
str(FinalData.melt)



#Q4.Create a two column plot with type and value using the melt data.
ggplot(data = FinalData.melt , aes(x= type, y=value)) + geom_boxplot()+ facet_wrap(~ variable, ncol = 2)



#Q5.Normalize the data and recreate the data frame.
FinalData.scale = data.frame(scale(FinalData[, -8]))
str(FinalData.scale)
summary(FinalData.scale)

#OR

FinalData.scale.v2=data.frame(scale(FinalData[-8], center = apply(FinalData[-8],2,mean),scale =apply(FinalData[-8],2,sd) ))
str(FinalData.scale.v2)

# Both FinalModelScale.v2 and FinalModelScale gives us the same result. Anyone could be used.




#Q6.redo the plot
FinalData.scale$type <- FinalData$type
FinalData.scale
FinalData.scale.melt = melt(FinalData.scale, id.var ="type")
ggplot(data = FinalData.scale.melt , aes(x= type, y=value)) + geom_boxplot()+ facet_wrap(~ variable, ncol = 2)



#Q7.Explain if there are any colinearity issues
cor(FinalData.scale[-8])
# NO colinearity between variables, But the closet colinearity can be found in variables skin and BMI 0.647422386



#Q8.	What is the ratio of Yes and No in the response 
table(FinalData.scale$type)
summary(FinalData.scale)




#Q9.Set a seed and create a  70 / 30 trainData / Test data set 
set.seed(1511) # create random numbers
summary(FinalData.scale)
FinalData.scale
ind <- sample(2, nrow(FinalData.scale), replace = TRUE, prob=c(0.7, 0.3))
# trainDataing dataset
trainData = FinalData.scale[ind == 1, ] 
# test dataset
test = FinalData.scale[ind == 2, ] 
str(trainData)
str(test)


#Q10.train the model and come up with the optimal cluster size 

trainDataForCluster = trainData[, -8]
wss = (nrow(trainDataForCluster)-1)*sum(apply(trainDataForCluster,2,var))
for (i in 1:6) {
  wss[i] = sum(kmeans(trainDataForCluster, centers = i)$withinss)
}
plot(1:6, wss, type="b", xlab="Number of Clusters", ylab="Within groups sum of squares")


#NbClust
library(NbClust)
nc = NbClust(trainDataForCluster, min.nc =2, max.nc = 15, method = "kmeans")
table(nc$Best.nc[1,])
# majority of them say 3 clusters
barplot((nc$Best.nc[1,]),xlab="Number of Clusters", ylab="No of criteria", main = "Number of clusters choosen by 26 criteria" )




#Q11.	Calculate the confusion matrix and the error  
grid1 = expand.grid(.k = seq(2, 20, by = 1))
control = trainControl(method = "cv")
set.seed(1511)

knn.trainData <- train(type ~ ., data = trainData, method = "knn", trControl = control, tuneGrid = grid1)
knn.trainData
knn.test <- knn(trainData[,-8], test[, -8], trainData[, 8], k = 6)
table(knn.test, test$type)

prob.agree <- (75 +32) / 147 #accuracy
prob.chance <- ((75 + 22) / 147) * ((75 + 18) / 147)
prob.chance
kappa <- (prob.agree - prob.chance) / (1 - prob.chance)
kappa




#Q12.	Use  the kknn package and its train.kknn() function to select the optimal weighting scheme
model <- train.kknn(formula = type ~ ., data = data.frame(trainData), kmax = 6)
train.kknn(formula = type ~ ., data = data.frame(trainData), kmax = 6)


#Q13.	Create a new training set using kknn library In the kknn trainData data set use kmax = 25 and distance 2 for absolute distance and for kernel use c("rectangular", "triangular", "epanechnikov"))

set.seed(1511)
kknn.trainData <- train.kknn(type ~ ., data = data.frame(trainData), kmax = 60, distance = 2, kernel = c("rectangular", "triangular", "epanechnikov"))
plot(kknn.trainData)
kknn.trainData

#Q14.	Find the best k  


#Q15.	use the kknn to predict the test data and build the confusion matrix; calculate the accuracy  
kknn.pred <- predict(kknn.trainData, test[,-8])
kknn.pred 
table(kknn.pred, test$type)


#Q16.	use the e1071 package to build the SVM models   

linear.tune <- tune.svm(type ~ ., data = data.frame(trainData), kernel = "linear", cost = c(0.001, 0.01, 0.1, 1, 5, 10))
best.linear <- linear.tune$best.model
best.linear
tune.test <- predict(best.linear, newdata = test)
table(tune.test, test$type)


set.seed(1511)
poly.tune <- tune.svm(type ~ ., data = data.frame(trainData), kernel = "polynomial", degree = c(3, 4, 5),coef0 = c(0.1, 0.5, 1, 2, 3, 4))
best.poly <- poly.tune$best.model
poly.test <- predict(best.poly, newdata = test)
table(poly.test, test$type)
(81 + 26) / 147

set.seed(1511)
rbf.tune <- tune.svm(type ~ ., data = trainData, kernel = "radial", gamma = c(0.1, 0.5, 1, 2, 3, 4))
best.rbf <- rbf.tune$best.model
best.rbf
rbf.test <- predict(best.rbf, newdata = test)
table(rbf.test, test$type)

(73+21)/147



set.seed(1511)
sigmoid.tune <- tune.svm(type ~ ., data = data.frame(trainData),  kernel = "sigmoid",gamma = c(0.1, 0.5, 1, 2, 3, 4),coef0 = c(0.1, 0.5, 1, 2, 3, 4))
summary(sigmoid.tune)
best.sigmoid <- sigmoid.tune$best.model
best.sigmoid
sigmoid.test <- predict(best.sigmoid, newdata = test)
table(sigmoid.test, test$type)





