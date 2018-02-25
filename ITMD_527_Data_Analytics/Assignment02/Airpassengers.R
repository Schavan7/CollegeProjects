AirPassengers
x = ts(AirPassengers, frequency = 12,start = c(1946,1), end= c(1955,12))
plot(AirPassengers)

abline(reg=lm(AirPassengers~time(AirPassengers)))

cycle(AirPassengers)

# Transform the data to make the variance same


#Calclulate Moving avg
library(TTR)
x <- SMA(TimeSeriesData,n=3)
plot.ts(x)

x <- SMA(TimeSeriesData,n=8)
plot.ts(x)

y <- decompose(TimeSeriesData)
plot(y)

acf(log(TimeSeriesData))
acf(log(TimeSeriesData),lag.max=15)

#---------------------------------------------------------


library(readr)
library(tseries)
library(stats)
library(lmtest) 
library(ast)
library(astsa)
library(xts)

library(zoo)
TimeSeries <-  read.csv("Mydata.csv",header= TRUE,sep =",")
TimeSeries <- as.xts(TimeSeries)
str(TimeSeries)
class(TimeSeries)
summary(TimeSeries)
TimeSeries$Month
TimeSeries$Temperature
plot.ts(TimeSeries)
TimeSeries$ID = NULL
TimeSeries

#Prepare the data as time series between 1946 and 1959 based on monthly frequency
TimeSeriesData = ts(TimeSeries$Temp, frequency = 12,start = c(1946,1), end= c(1959,12))
TimeSeriesData
str(TimeSeriesData)
summary(TimeSeriesData)


class(TimeSeriesData)
start(TimeSeriesData)
end(TimeSeriesData)
frequency(TimeSeriesData)
plot(TimeSeriesData)
#plot.ts(TimeSeriesData)# dont do this
abline(reg=lm(TimeSeriesData~time(TimeSeriesData)))

boxplot(TimeSeriesData~cycle(TimeSeriesData))
# we can see from the box plot that the temperature during oct, nov,dec,jan and feb,are very high
# compared to rest of the months.


# Transform the data to make the variance same
# if the mean, variance and co-variance become equal, the data
#becomes stationay and hence we can apply time series.

# 1st apply the log function to make the varianve equal
plot(log(TimeSeriesData))

# 2nd apply the diff function to the logged data make the mean equal
plot(diff(log(TimeSeriesData)))

# Now the series is stationary and we can apply an model on it.

# AR I MA
# p d  q

# apply acf when the series if not stationary
acf(TimeSeriesData)


#Determine the value of q (MA)- value is q 2
acf(diff(log(TimeSeriesData))) 

#Determine the value of p(AR) - value is 1
pacf (diff(log(TimeSeriesData)))

#Determine the value of d --> value is 1 , as we have done diff only once


# ARIMA MODEL
#c(p,d,q)
# not adding the diffrenetiation as we gave aleady specifies the d in the model
# Below code not running
fit =  arima(log(TimeSeriesData), c(1,1,2) , seasonal = list(order = c(1,1,2), period = 12), method="CSS")
#fit =  arima(log(TimeSeriesData), order = c(1,1,2) ,list(order = c(1,1,2), period = NA)) 

pred = predict(fit, n.ahead = 10 * 12)
pred1 = 2.718^pred$pred
ts.plot(TimeSeriesData, 2.718^pred$pred, log = "y", lty = c(1,3))


#testing the model
TestData = ts(TimeSeries$Temp, frequency = 12,start = c(1946,1), end= c(1959,12))
fit1 =  arima(log(TestData), c(1,1,2) , seasonal = list(order = c(1,1,2), period = 12), method="CSS")
pred = predict(fit1, n.ahead = 10 * 12)
pred1 = 2.718^pred$pred
data1 = head(pred1,12)

predicted_1960 = round(data1, digits = 0)
original_1960 = tail(TimeSeriesData,12)

ts.plot(TimeSeriesData, 2.718^pred$pred, log = "y", lty = c(1,3))





