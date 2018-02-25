
library(readr)
library(tseries)
library(stats)
library(lmtest) 
library(xts)
library(zoo)
library(quadprog)
#library(ast)
#library(astsa)



TimeSeries <-  read.csv("Mydata.csv",header= TRUE,sep =",")
head(TimeSeries)
plot(TimeSeries)
#TimeSeries <- as.xts(TimeSeries)

plot.ts(TimeSeries)
str(TimeSeries)
class(TimeSeries)
summary(TimeSeries)

#TimeSeries$ID = NULL
TimeSeries


#Prepare the data as time series between 1946 and 1959 based on monthly frequency
TimeSeriesData = ts(TimeSeries$Temp, frequency = 12,start = c(1946,1), end= c(1959,12))
summary(TimeSeriesData)
str(TimeSeriesData)
plot(TimeSeriesData)




#Q1: How do you interpret the data as it relates to time series data pattern?
class(TimeSeriesData)
start(TimeSeriesData)
end(TimeSeriesData)
frequency(TimeSeriesData)

#plot.ts(TimeSeriesData)# dont do this
abline(reg=lm(TimeSeriesData~time(TimeSeriesData)))
boxplot(TimeSeriesData~cycle(TimeSeriesData))
TimeSeriesComponents <- decompose(TimeSeriesData)
plot(TimeSeriesComponents)
qqnorm(TimeSeriesData)
qqline(TimeSeriesData, col=2)

# we can see from the box plot that the temperature during oct, nov,dec,jan and feb,are 
#very high as its very cold during #these months as compared to rest of the months.


#Q2 What is the best AR method to fit the data and Why?


#Calculate acf & pacf for this data and plot them
# Transform the data to make the variance same
# if the mean, variance and co-variance become equal, the data
#becomes stationay and hence we can apply time series.


# apply acf when the series if not stationary
acf(TimeSeriesData)



# apply log and  diff function to the logged data make the mean equal
timesseries <- diff(log(TimeSeriesData))

# Now the series is stationary and we can apply an model on it.

# AR I MA
# p d  q

#Determine the value of q (MA)- value is q 2
acf(timesseries) 

#Determine the value of p(AR) - value is p 1
pacf(timesseries)

#Determine the value of d --> value is 1 , as we have done diff only once


#Q3 Based on these plots, do you think it is necessary to adjust the data for 
# seasonality by modifying p,d or q?
# Use auto.arima to find the p,d,q values
??

auto.arima(TimeSeriesData ,ic= 'bic')
arimaTimeSeries <- arima(TimeSeriesData,c(3,0,1))
plot(arimaTimeSeries$residuals)

#With the stationary data
arimaTimeSeries <- arima((timesseries),c(1,0,2))
plot(arimaTimeSeries$residuals)

#arimaTimeSeries <- arima(diff(log(TimeSeriesData)),c(1,0,2))
#plot(arimaTimeSeries$residuals)




arimaTimeSeriesForecast <- forecast(arimaTimeSeries, h=20)
arimaTimeSeriesForecast
plot(arimaTimeSeriesForecast)
acf(arimaTimeSeries$residuals, lag.max = 20) 
Box.test(arimaTimeSeries$residuals, type = "Ljung-Box")




#Q4 What is the recommended p,d,q for this time series
# Answer: The recommended  p,d,q are 3,0,1.


#Q5 Try different combinations of p,d,q and explain what values you would use and why?










#Q6 Use various models to forecast and analyze the output; explain which combination of p,d,q is better and why


# ARIMA MODEL
#c(p,d,q)
# not adding the diffrenetiation as we gave aleady specifies the d in the model
# Below code not running
fit <-  arima(log(TimeSeriesData), c(1,1,2) , seasonal = list(order = c(1,1,2), period = 12), method="CSS")
#fit <-  arima(log(TimeSeriesData), order = c(1,1,2) ,list(order = c(1,1,2), period = NA)) 

pred <- predict(fit, n.ahead = 10 * 12)
pred1 <- 2.718^pred$pred
ts.plot(TimeSeriesData, 2.718^pred$pred, log = "y", lty = c(1,3))


#testing the model
TestData <- ts(TimeSeries$Temp, frequency = 12,start = c(1946,1), end= c(1959,12))
fit1 <-  arima(log(TestData), c(1,1,2) , seasonal = list(order = c(1,1,2), period = 12), method="CSS")
pred <- predict(fit1, n.ahead = 10 * 12)
pred1 <- 2.718^pred$pred
data1 <- head(pred1,12)

predicted_1960 = round(data1, digits = 0)
original_1960 = tail(TimeSeriesData,12)

predicted_1960
original_1960

ts.plot(TimeSeriesData, 2.718^pred$pred, log = "y", lty = c(1,3))


#Prediction Model 2 
TimeSeriesforecasts <- HoltWinters(TimeSeriesData, beta=FALSE, gamma=FALSE)

TimeSeriesforecasts$fitted
plot(TimeSeriesforecasts,col = "green")
TimeSeriesforecasts$SSE

#??forecast
HoltWinters(TimeSeriesData, beta=FALSE, gamma=FALSE, l.start=11.506)
library(forecast)
TimeSeriesforecasts2 <- forecast(TimeSeriesData, h=8)
TimeSeriesforecasts2
plot(TimeSeriesforecasts2)

# verify correlations between forecast errors for successive predictions
# if yes try another forecasting technique. 
acf(TimeSeriesforecasts2$residuals, lag.max = 15)
TimeSeriesforecasts2$residuals
TimeSeriesforecasts2$residuals[1] = 1
acf(TimeSeriesforecasts2$residuals, lag.max=20)


#Q7 – Plot the forecast model and perform LJung-Box test and explain the results; Do this for a minimum of two forecast models based on your choice of p,d,q
# test whether there is significant evidence for non-zero correlations at lags 1-20
Box.test(TimeSeriesforecasts2$residuals, lag=70, type="Ljung-Box")# pvalue to be more than 0.5
#Box.test(TimeSeriesforecasts2$residuals, lag=15, type="Ljung-Box")
#plot.ts(TimeSeriesforecasts2$residuals)
  
# after the Ljung test, the p value has to be higher than 0.5 to reject the null hypothesis and to say that there is no
# corelation

plotForecastErrors <- function(forecasterrors) { 
  mybinsize <- IQR(forecasterrors)/4 
  mysd <- sd(forecasterrors)
  mymin <- min(forecasterrors) - mysd*5 
  mymax <- max(forecasterrors) + mysd*3 # generate normally distributed data with mean 0 and standard deviation mysd 
  mynorm <- rnorm(10000, mean=0, sd=mysd) 
  mymin2 <- min(mynorm) 
  mymax2 <- max(mynorm) 
  if (mymin2 < mymin) { mymin <- mymin2 } 
  if (mymax2 > mymax) { mymax <- mymax2 } 
  mybins <- seq(mymin, mymax, mybinsize)
  hist(forecasterrors, col="red", freq=FALSE, breaks=mybins) 
  myhist <- hist(mynorm, plot=FALSE, breaks=mybins) 
  points(myhist$mids, myhist$density, type="l", col="blue", lwd=2) }

plotForecastErrors(TimeSeriesforecasts2$residuals)


