libs <- c("dplyr", "ggplot2", "plotly", "reshape2", "magrittr", "ggthemes", 
          "tidyr", "DT", "lubridate", "stringr", "RColorBrewer")
lapply(libs, require, character.only = TRUE)

ml.libs <- c("rpart", "rpart.plot", "party", "rattle", "partykit", "caret", "randomForest", "xgboost", "rpart.plot")

lapply(ml.libs, require, character.only = TRUE)


# Reading the file---------------
Survey = read.csv("survey.csv", header = TRUE)
sapply(Survey, function(x) sum(is.na(x)))

# Checking the NA values and removing those values---------------
## Remove the Comments and state column from the file itself
Survey$Timestamp= NULL

Survey %<>% dplyr::select(-c(comments , state, work_interfere))
Survey %<>% remove_missing()


# Data Cleaning, gender names are written in many forms
# below are some of the methods used to clean the data

Survey$Gender
Survey$Gender %<>% str_to_lower()
Survey$Gender %>% unique()


male_str <- c("male", "m", "male-ish", "maile", "mal", "male (cis)", "make", "male ", "man","msle", "mail", "malr","cis man")

trans_str <- c("trans-female", "something kinda male?", "queer/she/they", "non-binary","nah", "all", "enby", "fluid", "genderqueer", "androgyne", "agender", "male leaning androgynous", "guy (-ish) ^_^", "trans woman", "neuter", "female (trans)", "queer", "ostensibly male, unsure what that really means" )

female_str <- c("cis female", "f", "female", "woman",  "femake", "female ","cis-female/femme", "female (cis)", "femail")


# Using a loop to categorize the data into only 3 forms (Male, Female and trans)

for(i in 1:nrow(Survey)){
  for(k in 1:length(str_detect(Survey$Gender[i],trans_str)))
    if(str_detect(Survey$Gender[i],trans_str)[k] == TRUE){
      Survey$Gender[i] <- "trans"
    }
  
  for(m in 1:length(str_detect(Survey$Gender[i],male_str)))
    if(str_detect(Survey$Gender[i],male_str)[m] == TRUE){
      Survey$Gender[i] <- "male"
    }
  
  for(o in 1:length(str_detect(Survey$Gender[i],female_str)))
    if(str_detect(Survey$Gender[i],female_str)[o] == TRUE){
      Survey$Gender[i] <- "female"
    }
}

Survey %<>% filter(Gender != "a little about you")
Survey %<>% filter(Gender != "guy (-ish) ^_^")
Survey %<>% filter(Gender != "p")

Survey$Gender %>% unique() # we now get only three values - Male, female and Trans
str(Survey)
attach(Survey)



# Age categorization
Age =Survey$Age<-cut(Survey$Age, c(-Inf,20,35,65,Inf))
Gender
names(Survey$Country)
Survey$Country

Survey$no_employees <- as.factor(revalue(Survey$no_employees, c("1-5"="1", "6-25"="2", "26-100"="3", "100-500"="4", "500-1000"="5", "More than 1000"="6")))
Survey$treatment <- as.numeric(revalue(Survey$treatment, c("No"="0", "Yes"="1")))

# Age categorization
Survey$Age<-cut(Survey$Age, c(-Inf,20,35,65,Inf))


mental_all_var <- lm(treatment~
  family_history+
  Survey$Age+
  remote_work+
    Survey$no_employees+
  tech_company+
  benefits+
  care_options+
  wellness_program+
  seek_help+
  anonymity+
  leave+
  mental_health_consequence+
  phys_health_consequence+
  coworkers+
  supervisor+
  mental_health_interview+
  phys_health_interview+
  mental_vs_physical+
  obs_consequence)

summary(mental_all_var)
coefficients(mental_all_var)


mental_var_tree <- treatment ~ family_history+Age+remote_work+no_employees
#plot(mental_var_tree)
train %<>% remove_missing()
treeRF_mental <- randomForest(mental_var_tree, data = train, ntree=25, proximity = T)
dataimp_spam <- varImpPlot(treeRF_mental, main = "Importance of each variable")


?cut







#class and level of all variable

Survey$Gender <- as.factor(Survey$Gender)
levels(Gender)

#Age
#treatment = as.numeric(treatment)
#Model = lm(treatment ~ Age , data = Survey)




















