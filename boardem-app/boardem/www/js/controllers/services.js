/*-----------------------------------------------------
								EVENT SERVICE
-----------------------------------------------------*/
appCtrl.service('EventService', ['$rootScope','$http','RestService','UserService', function ($rootScope,$http,RestService,UserService) {
	
        var endpoint = 'event/';
				
				//Any other http request will be used as a service function
        this.getEvent = function (base_url,eventId,callback) {
			     return $http.get(base_url+endpoint+eventId);
        };

        this.joinEvent = function(base_url,event_id,user_id){
          return $http.post(base_url+endpoint+event_id+"/join?"+"user_id="+user_id);
        };

        this.leaveEvent = function(base_url,event_id,user_id){
           return $http.post(base_url+endpoint+event_id+"/leave?"+"user_id="+user_id);         
        }

        this.getTimeDifference = function(date){
          var cur = "20"+date.replace(/-/g,"/");

          var millis1 = Date.parse(cur);
          var millis2 = Date.now();

          var diff = Math.abs(millis2 - millis1);

          var days = Math.ceil(diff / (1000 * 3600 * 24));

          var hours = Math.ceil((diff %(1000 * 3600 * 24)) / (1000*3600));

          var min = Math.ceil((diff % (1000 * 3600) / (1000*60)))

          return {days:days,hours:hours};

        };

        this.isParticipant = function(user_id,event){
          for (id in event.participants){
            if (event.participants[id] == user_id){
              return false;
            }
          }

          return true;
        }

        this.isOwner = function(user_id,event){
          return user_id == event.owner;
        }

        this.getDistanceInKM = function(lat1,lng1,lat2,lng2){
          var R = 6371000; // metres
          var φ1 = this.toRad(lat1);
          var φ2 = this.toRad(lat2);
          var Δφ = this.toRad(lat2-lat1);
          var Δλ = this.toRad(lng2-lng1);

          var a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
                  Math.cos(φ1) * Math.cos(φ2) *
                  Math.sin(Δλ/2) * Math.sin(Δλ/2);
          var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

          var d = R * c;

          return (d/1000).toFixed(2);
        };

        this.toRad = function(val){
            /** Converts numeric degrees to radians */
            return val * Math.PI / 180;
        };
				
				this.loadEvents = function(){
					var events = [];
					var self = this;
					RestService.getEvents($rootScope.SERVER_LOCATION).success(function(res){
						var event_ids = res.extra;
						for (id in event_ids){
							var id_string = event_ids[id];
							self.getEvent($rootScope.SERVER_LOCATION,event_ids[id]).success(function(res){
									events.push(res.extra);
									var num = events.length-1;
									events[num].time = self.getTimeDifference(res.extra.date);
									events[num].canJoin = self.isParticipant($rootScope.user_id,events[num]);
									events[num].isOwner = self.isOwner($rootScope.user_id,events[num]);

									UserService.getUser($rootScope.SERVER_LOCATION,events[num].owner).success(function(response){
										events[num].owner_profile = response.extra;
									});
							});
						}
					}).error(function(error){
						$ionicPopup.alert({
							title: "Failed to Retrieve events.",
							template: "Error: "+error
						});
					});
					return events;
				};
	}]);

/*-----------------------------------------------------
								REST SERVICE
-----------------------------------------------------*/
appCtrl.service('RestService', ['$http', function ($http) {
	
        var endpoint = 'event';

        this.getEvents = function (base_url) {

						return $http.get(base_url+endpoint);

        };
}]);

/*-----------------------------------------------------
								SEARCH CRITERIA SERVICE
-----------------------------------------------------*/
appCtrl.service('SearchCriteria', function () {
		
		var dateObject = new Date();
		var daysArray = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];
		var monthsArray = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
		//Get date in numbers
		var month = dateObject.getMonth();
		var day = dateObject.getDate();
		var year = dateObject.getFullYear();
		//Get date in string format
		var monthString = monthsArray[dateObject.getMonth()];
		var dayString = daysArray[dateObject.getDay()];
	
		var locationSearch = 5;
		var gameType = "all";
		
		/*Date based search features*/
		this.changeDateSearch = function (direction) {
					
				var newDateObject = new Date(year, month, day);
				if(direction == 1 || direction == "1") {
					newDateObject.setDate(newDateObject.getDate()+1);
				} else {
					newDateObject.setDate(newDateObject.getDate()-1);
				}
			
				//Get date in numbers
				month = newDateObject.getMonth();
				day = newDateObject.getDate();
				year = newDateObject.getFullYear();
				//Get date in string format
				monthString = monthsArray[newDateObject.getMonth()];
				dayString = daysArray[newDateObject.getDay()];
		};
		
		this.getSearchDate = function () {
					
				var datestring = day + " " + monthString + " " + year;
				return datestring;
		};
		
		this.getDay = function () {
					
				return day;
		};
		
		this.getMonth = function () {
				return monthString;
		};
		
		this.getYear = function () {
				return year;
		};
		
		/*Location based search features*/
		this.changeDistance = function(direction){
				if(direction == 1 || direction == "1") {
					if(locationSearch<10)locationSearch++;
					else if(locationSearch<30)locationSearch = locationSearch+5;
					else if(locationSearch < 50) locationSearch = locationSearch+10;
				}else{
					if(locationSearch>30)locationSearch = locationSearch-10;
					else if(locationSearch>10)locationSearch = locationSearch-5;
					else if(locationSearch > 1) locationSearch--;
				}
		}
		
		this.getLocationSearch = function () {
				
				return locationSearch;
		};
		
		
});

/*-----------------------------------------------------
								USER SERVICE
-----------------------------------------------------*/
appCtrl.service("UserService",['$http',function($http){

	var endpoint = "users/"
	this.getUser = function(base_url,userid){
		return $http.get(base_url+endpoint+userid);
	}
}]);