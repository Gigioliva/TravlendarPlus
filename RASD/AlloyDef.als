open util/boolean
open util/integer

// Definition of class User
sig User{
username: one Username,
name: one Name,
surname: one Name,
mail: one Mail,
phone: one Phone,
drivingLicense: lone DrivingLicense,
creditCard: one CreditCard,
ticketList: set Ticket,
meansPref: Means -> Bool,
breakPref: some Event,
journeyList: set Schedule,
sharedSchedule: set Schedule
}

// Definition of class Ticket
sig Ticket{
ID: one ID,
evaluDate: one Int,
purchaseDate: one Int,
cost: one Int,
validMeans: some TypeMeans,
type: one TypeTicket
}

// Definition of class Means
sig Means{
name: one MeansName,
type: one TypeMeans,
status: one Bool 
}

// Definition of class Journey
sig Journey{
startingTime: one Int,
duration: one Int,
meansUsed: some Means,
destEvent: one Event,
startPosition: one Position
}

// Definition of class Schedule
sig Schedule{
day: one Int,
journeyList: some Journey,
ID: one ID
}

// Definition of class Position
sig Position{
longitude: one Float,
latitude: one Float
}

// Definition of class Weather
sig Weather{
date: one Int,
type: one TypeWeather,
location: one Position
}

// Definition of class Event
sig Event{
name: one EventName,
start: one Int,
duration: one Int,
location: one Position,
type: one EventType
}

// Basic definition of utility classes
sig Name{}
sig Mail{}
sig Phone{}
sig DrivingLicense{}
sig CreditCard{}
sig ID{}
sig Username{}
sig MeansName{}
sig EventName{}
sig Float{}

// Definition of all the enum required with the matching options
enum EventType {MEETING, BREAK, OTHEREVENT}
enum TypeTicket {SINGLE, SUBSCRIPTION, CARNET, OTHER}
enum TypeMeans {CAR, TRAIN, BUS, SUBWAY, BIKE, WALK}
enum TypeWeather {SUNNY, WINDY, STORMY, SNOWY, CLOUDY, RAINY}

pred show(u: User){}

// This fact contribute to create a more readable graph while we generate the world show below
fact WorldOptimalView{
		#User=2 and (all user: User | #user.journeyList=2)
}

// The duration of the journey must be greater or at least zero
fact JourneyDurationGreaterThanZero{
	all j: Journey | j.duration>=0
}

// The duration of the event must be greater or at least zero
fact EventDurationGreaterThanZero{
	all e: Event | e.duration>=0
}

// The start time of the event must be greater or at least zero
fact EventStartGreaterThanZero{
	all e: Event | e.start>=0
}

// The start time of the journey must be greater or at least zero
fact JourneyStartGreaterThanZero{
	all j: Journey | j.startingTime>=0
}

// The date in the weather classes must be greater or at least zero
fact WheatherDateGreaterThanZero{
	all w: Weather | w.date>=0
}
 
// The day which refer the schedule must be greater or at least zero
fact ScheduleDayGreaterThanZero{
	all s: Schedule | s.day>=0
}

// The purchase date of the ticket must be greater or at least zero
fact TicketPurchaseDateGreaterThanZero{
	all t: Ticket | t.purchaseDate>=0
}

// The username of the user must be distinct
fact UsernameDistinct{
	no disj user1,user2: User | user1.username=user2.username
}

// The mail of the user must be distinct
fact MailDistinct{
	no disj user1,user2: User | user1.mail=user2.mail
}

// The driving licence of the user must be distinct
fact DrivingLicenseDistinct{
	no disj user1,user2: User | user1.drivingLicense=user2.drivingLicense
}

// The schedule date of the schedule of a single user must be distinct
fact ScheduleDateDistinct{
	all user: User | (no disj schedule1,schedule2: user.journeyList | schedule1.day=schedule2.day)
}

// The ticket ID must be distinct
fact TicketIdDistinct{
	no disj ticket1,ticket2 : Ticket | ticket1.ID=ticket2.ID
}

// The cost of the ticket must be greater than zero
fact TicketCostPositive{
	all ticket1 : Ticket | ticket1.cost>0
}

// For one positon in one date is possible to have only one weather
fact OneWeatherForPositionDate{
	no disj w1,w2: Weather | (w1.location=w2.location and w1.date=w2.date)
}

// All position must be different
fact PostionDistinct{
	no disj p1,p2: Position | (p1.latitude=p2.latitude and p1.longitude=p2.longitude)
}

// The means that the user use must be one of the selected
fact RightJourneyMeans{
	all user1 : User | (user1.journeyList.journeyList.meansUsed.type in (user1.meansPref.boolean/True).type)
}

// All the means must be a boolean associate
fact AllMeansWithPref{
	all m : Means, u : User | m.(u.meansPref)=boolean/True or m.(u.meansPref)=boolean/False
}

// Is not possible to use damaged means for the journey
fact NoDamageMeans{
	all user1 : User | user1.journeyList.journeyList.meansUsed.status=boolean/True
}

// In all journey the start and end point must be different
fact DifferentPositionStartEnd{
	all journey1 : Journey | journey1.startPosition!=journey1.destEvent.location
}

// It is not possible to use the bike while rain
fact RainyDay{
	all journey1 : Journey, weather1 : Weather | (journey1.startingTime=weather1.date and weather1.type=RAINY) implies !(journey1.meansUsed.type=BIKE)
}

// It is not possible to associate a ticket to the travel mode "WALK", "BIKE" and "CAR"
fact TicketAssociated{
	all user1 : User | (user1.journeyList.journeyList.meansUsed.type!=WALK and user1.journeyList.journeyList.meansUsed.type!=BIKE and user1.journeyList.journeyList.meansUsed.type!=CAR) implies (user1.ticketList.validMeans.type=user1.journeyList.journeyList.meansUsed.type and user1.ticketList.evaluDate >= user1.journeyList.day)
}

// in breakPref the type of the event is "BREAK" 
fact BreakEvent{
	all user1 : User | user1.breakPref.type=BREAK
}

// All the schedule ID must be different
fact ScheduleIdDistinct{
	no disj sched1,sched2 : Schedule | sched1.ID=sched2.ID
}

//  All the means name must be different
fact MeansNameDistinct{
	no disj m1,m2 : Means | m1.name=m2.name
}

// For all ticket the evaluation date must be greater or at least equal to the purchase date
fact TicketDateControl{
	all ticket : Ticket | ticket.evaluDate>=ticket.purchaseDate
}

// In every schedule there is at least a breack event for every break preference
fact AtLeastOneBreak{
	all user: User | (all break: user.breakPref, schedule: user.journeyList | (one event: schedule.journeyList.destEvent | event = break))
}

// Every schedule in the list of shared event of a user must be in a journeyList of an another user
fact SharedEventExist{
	all user: User | (all shared: user.sharedSchedule | (one user2: User | (one sched: user2.journeyList|sched.ID=shared.ID)))
}

// All event must be associated to a journey
fact EveryEventInJourney{
	all event: Event | (some journey: Journey | event in journey.destEvent)
}

// All journey must be associated to a schedule
fact EveryJourneyInSchedule{
	all journey: Journey | (one sched: Schedule | journey in sched.journeyList)
}

// All schedule must be associated to a user
fact EveryScheduleIsOfAUser{
	all sched: Schedule | (one user: User | sched in user.journeyList)
}

// There is no event or journey that overlap for a schedule
fact NoEventsOverlap{
	all user: User, schedule: user.journeyList |no disj j1, j2: schedule.journeyList |
	( (j2.startingTime < ((j1.destEvent.start).plus[j1.destEvent.duration])) and (j2.startingTime> j1.startingTime) )
}

// Every journey start in a different time from another
fact JourneyStartDistinct{
	all u: User | (all sh: u.journeyList | (no disj j1,j2: sh.journeyList | j1.startingTime=j2.startingTime))
}

// Is not possible to use the car for a journey if the field of the driving licence of the user is empty
fact NoCarWithoutDrivingLicence{
	all user: User | (user.drivingLicense = none) implies (not (CAR in boolean/True.(~(user.meansPref)).type ) )
}

// The journey must finish at the starting time of the event
fact JourneyDuration{
	all journey: Journey | ( ((journey.startingTime).plus[journey.duration]) = journey.destEvent.start)
}

// All ticket must be valid for only public means
fact TicketOnlyForPublicMeans{
	all ticket : Ticket | ( (not (WALK in ticket.validMeans) ) and (not (BIKE in ticket.validMeans) ) and (not (CAR in ticket.validMeans) ) )
}

// Assert that checks there is no event or journey that overlap for a schedule
assert NoEventsOverlap{
	all user: User, schedule: user.journeyList |no disj j1, j2: schedule.journeyList |
	( (j2.startingTime < ((j1.destEvent.start).plus[j1.destEvent.duration])) and (j2.startingTime> j1.startingTime) )
}

// Assert which checks that in every schedule there is at least a break event for every break preference
assert AtLeastOneBreak{
	all user: User | (all break: user.breakPref, schedule: user.journeyList | (one event: schedule.journeyList.destEvent | event = break))
}

//Assert which checks that every schedule in the list of shared event of a user must be in a journeyList of an another user
assert SharedEventExist{
	all user: User | (all shared: user.sharedSchedule | (one user2: User | (one sched: user2.journeyList|sched.ID=shared.ID)))
}

run show for 4 but 5 int

check NoEventsOverlap
check SharedEventExist
check AtLeastOneBreak
