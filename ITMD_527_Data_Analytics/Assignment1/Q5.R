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
FinalModel = rbind(Pima.tr, Pima.te)
summary(FinalModel)
str(FinalModel)
plot(Pima.te)
plot(Pima.tr)
plot(FinalModel)
View(FinalModel)




#Q3. Use the melt function by "type"
FinalModel.melt = melt(FinalModel , id.var = "type")
FinalModel.melt



#Q4.Create a two column plot with type and value using the melt data.
ggplot(data = FinalModel.melt , aes(x= type, y=value)) + geom_boxplot()+ facet_wrap(~ variable, ncol = 2)








#Q5.Normalize the data and recreate the data frame.
FinalModel.scale = data.frame(scale(FinalModel[, -8]))
str(FinalModel.scale)
summary(FinalModel.scale)



FinalModelScale.v2=data.frame(scale(FinalModel[-8], center = apply(FinalModel[-8],2,mean),scale =apply(FinalModel[-8],2,sd) ))
str(FinalModelScale.v2)

# Both FinalModelScale.v2 and FinalModelScale gives us the same result. Anyone could be used.



#Q6.redo the plot
# the below is not running look for it ***********

FinalModel.scale.melt = melt(FinalModel.scale, id.var ="type")
ggplot(data = FinalModelScale.melt , aes(x= type, y=value)) + geom_boxplot()+ facet_wrap(~ variable, ncol = 2)

str(FinalModel)
str(FinalModel.scale)

#Q7.Explain if there are any colinearity issues
cor(FinalModel.scale[-8])

# no colinearity



#Q8.	What is the ratio of Yes and No in the response 
FinalModel.scale.type = FinalModel$type
FinalModel.scale.type
table(FinalModel.scale.type)


#Q9.Set a seed and create a 70 / 30 train / Test data set 
set.seed(1511) # create random numbers
ind = sample(2, nrow(FinalModel.scale), replace = TRUE, prob=c(0.7, 0.3))
train = FinalModel.scale[ind == 1, ] 
train = data.matrix(train)
# training dataset
test = FinalModel.scale[ind == 2, ] 
# test dataset
str(train)
str(test)


#Q10.Train the model and come up with the optimal cluster size 

## For the Train Model
wss = (nrow(train)-1)*sum(apply(train,2,var))
for (i in 2:6) {
  wss[i] = sum(kmeans(train, centers = i)$withinss)
}
plot(1:6, wss, type="b", xlab="Number of Clusters", ylab="Within groups sum of squares")

#NbClust
set.seed(1511)
nc = NbClust(train, min.nc =2, max.nc = 15, method = "kmeans")
table(nc$Best.nc[1,])
# majority of them say 3 clusters
barplot((nc$Best.nc[1,]),xlab="Number of Clusters", ylab="No of criteria", main = "Number of clusters choosen by 26 criteria" )




## For the Full Model
# scree plot
wss = (nrow(FinalModel.scale)-1)*sum(apply(FinalModel.scale,2,var))
for (i in 2:6) {
  wss[i] = sum(kmeans(FinalModel.scale, centers = i)$withinss)
}
plot(1:6, wss, type="b", xlab="Number of Clusters", ylab="Within groups sum of squares")

#OR

#NbClust
library(NbClust)
set.seed(1511)
nc = NbClust(FinalModel.scale, min.nc =2, max.nc = 15, method = "kmeans")
table(nc$Best.nc[1,])
# majority of them say 3 clusters
barplot((nc$Best.nc[1,]),xlab="Number of Clusters", ylab="No of criteria", main = "Number of clusters choosen by 26 criteria" )
# Based on the results and visualizing of the plots, by running the above two models, I came to a conclusion to use 3 clusters 
# for this model.




#Q11.	Calculate the confusion matrix and the error  

grid1 <- expand.grid(.k = seq(2, 20, by = 1))
control <- trainControl(method = "cv")

set.seed(1511)
knn.train <- train(type ~ ., data = train,
                   method = "knn",
                   trControl = control,
                   tuneGrid = grid1)
knn.train


knn.test <- knn(train[, -8], test[, -8], train[, 8], k = 6)

table(knn.test, test$type)





#Q12.	Use  the kknn package and its train.kknn() function to select the optimal weighting scheme
??grid1
grid1 <- expand.grid(.k = seq(2, 20, by = 1))
grid1
control <- trainControl(method = "cv")
control

set.seed(1511)
knn.train = train(type ~., data = train, method = "knn",trControl = control,tuneGrid = grid1)
knn.train

??knn
knn.test = knn(train [, -8], test[, -8], train[, 8], k = 6)
table(knn.test, test$type)


#Q13.	Create a new training set using kknn library In the kknn train data set use kmax = 25 and distance 2 for absolute distance and for kernel use c("rectangular", "triangular", "epanechnikov"))


#Q14.	Find the best k  


#Q15.	use the kknn to predict the test data and build the confusion matrix; calculate the accuracy  


#Q16.	use the e1071 package to build the SVM models   






