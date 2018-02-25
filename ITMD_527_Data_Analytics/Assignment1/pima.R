#Load the following packages

library(class) #k-nearest neighbors

library(kknn) #weighted k-nearest neighbors

library(e1071) #SVM

library(caret) #select tuning parameters

library(MASS) # contains the data

library(reshape2) #assist in creating boxplots

library(ggplot2) #create boxplots

library(kernlab) #assist with SVM feature selection

pima <- rbind(Pima.tr, Pima.te)
pima.melt <- melt(pima, id.var = "type")
pima
pima.melt

ggplot(data = pima.melt, aes(x = type, y = value)) + geom_boxplot() + facet_wrap(~ variable, ncol = 2)

pima.scale <- data.frame(scale(pima[, -8]))

str(pima.scale)

pima.scale$type <- pima$type



pima.scale.melt <- melt(pima.scale, id.var = "type")

ggplot(data = pima.scale.melt, aes(x = type, y = value)) + geom_boxplot() + facet_wrap(~ variable, ncol = 2)







cor(pima.scale[-8])

set.seed(7457)

ind <- sample(2, nrow(pima.scale), replace = TRUE, prob = c(0.7,
                                                            
                                                     0.3))

pima.scale

train <- pima.scale[ind == 1, ]
train

test <- pima.scale[ind == 2, ]

str(train)

str(test)

grid1 <- expand.grid(.k = seq(2, 20, by = 1))

control <- trainControl(method = "cv")

set.seed(7457)

knn.train <- train(type ~ ., data = train,
                   
                   method = "knn",
                   
                   trControl = control,
                   
                   tuneGrid = grid1)

??train
knn.train

knn.test <- knn(train[, -8], test[, -8], train[, 8], k = 6)

table(knn.test, test$type)

#calculate Kappa

prob.agree <- (75 +32) / 147 #accuracy

prob.chance <- ((75 + 22) / 147) * ((75 + 18) / 147)

prob.chance

kappa <- (prob.agree - prob.chance) / (1 - prob.chance)

kappa

set.seed(123)

kknn.train <- train.kknn(type ~ ., data = train, kmax = 25,
                         
                         distance = 2,
                         
                         kernel = c("rectangular", "triangular", "epanechnikov"))

plot(kknn.train)

kknn.train

kknn.pred <- predict(kknn.train, newdata = test)

table(kknn.pred, test$type)

#16
train
linear.tune <- tune.svm(type ~ ., data = train,
                        
                        kernel = "linear",
                        
                        cost = c(0.001, 0.01, 0.1, 1, 5, 10))

summary(linear.tune)

best.linear <- linear.tune$best.model

tune.test <- predict(best.linear, newdata = test)

table(tune.test, test$type)

set.seed(7457)

poly.tune <- tune.svm(type ~ ., data = train,
                      
                      kernel = "polynomial",
                      
                      degree = c(3, 4, 5),
                      
                      coef0 = c(0.1, 0.5, 1, 2, 3, 4))

summary(poly.tune)

best.poly <- poly.tune$best.model

poly.test <- predict(best.poly, newdata = test)

table(poly.test, test$type)

(81 + 26) / 147

set.seed(123)

rbf.tune <- tune.svm(type ~ ., data = train,
                     
                     kernel = "radial",
                     
                     gamma = c(0.1, 0.5, 1, 2, 3, 4))

summary(rbf.tune)

(73+21)/147

set.seed(123)

sigmoid.tune <- tune.svm(type ~ ., data = train,
                         
                         kernel = "sigmoid",
                         
                         gamma = c(0.1, 0.5, 1, 2, 3, 4),
                         
                         coef0 = c(0.1, 0.5, 1, 2, 3, 4))

summary(sigmoid.tune)

(82+35)/147