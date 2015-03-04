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