/*-----------------------------------------------------
								EVENT SERVICE
-----------------------------------------------------*/
appCtrl.service('EventService', ['$ionicPopup','$rootScope','$http','RestService','UserService', "UtilService",'ExperienceService',function ($ionicPopup,$rootScope,$http,RestService,UserService,UtilService,ExperienceService) {

        var endpoint = 'event/';

				//Any other http request will be used as a service function
        this.getEvent = function (base_url,eventId) {
			     return $http.get(base_url+endpoint+eventId);
        };

        this.joinEvent = function(base_url,event_id,user_id){
          return $http.post(base_url+endpoint+event_id+"/join?"+"user_id="+user_id);
        };

        this.leaveEvent = function(base_url,event_id,user_id){
           return $http.post(base_url+endpoint+event_id+"/leave?"+"user_id="+user_id);
        }

        this.getTimeDifference = function(date){
          var cur = "N/A";
          if (date){
            cur = date.replace(/-/g,"/");
          }

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

        this.getLocationFromCoords = function(lat,lng,location){
          var url = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lng+"&key=AIzaSyBinl1su9ywT5WVhBNmKugvdQHziIlCDyY";
          $http.get(url).success(function(res){
            location.push(res.results[0]);
          });

        }

        this.toRad = function(val){
            /** Converts numeric degrees to radians */
            return val * Math.PI / 180;
        };

				this.loadEvents = function(events){
					var self = this;
          UtilService.showLoad();
					RestService.getEvents($rootScope.SERVER_LOCATION).success(function(res){
						var event_ids = res.extra;
            self.getEventDetails(event_ids,events);
					}).error(function(res){
          UtilService.hideLoad();
        });

					//return events;
				};

        this.getEventComments = function(base_url,event_id,event){
          $http.get(base_url+endpoint+event_id+"/comments").success(function(res){
            event["comments"] = [];
            for (id in res.extra){
              event["comments"].push(res.extra[id]);
            }
          }).error(function(res){
          UtilService.hideLoad();
        });
        }

        this.comment = function(base_url,event_id,user_id,comment,event){
          var self = this;
          $http.post(base_url+endpoint+event_id+"/comment?",{user_id:user_id,comment:comment}).success(function(res){
            self.getEventComments(base_url,event_id,event);
          }).error(function(res){
          UtilService.hideLoad();
        });
        }

        this.getEventDetails = function(event_ids,events){
					var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
						"Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
          var self = this;
            for (id in event_ids){
              var id_string = event_ids[id];
              UtilService.showLoad();
              self.getEvent($rootScope.SERVER_LOCATION,event_ids[id]).success(function(res){
                  if (!events){
                    events = [];
                  }
                  events.push(res.extra);
                  var num = events.length-1;
                  events[num].time = self.getTimeDifference(res.extra.date);
                  events[num].canJoin = self.isParticipant($rootScope.user_id,events[num]);
                  events[num].isOwner = self.isOwner($rootScope.user_id,events[num]);

									var dateSplit = (res.extra.date).split(" ");
									var dateDate = dateSplit[0].split("-");
									var dateTime = dateSplit[1].split(":");
									events[num].eventDay = dateDate[2] + " " + monthNames[(dateDate[1]-1)];
									if(dateTime[0] > 12) events[num].eventTime = (dateTime[0]-12) + ":" + dateTime[1] + "pm";
									else events[num].eventTime = dateTime[0] + ":" + dateTime[1] + "am";
                  //self.getEventComments($rootScope.SERVER_LOCATION,event_ids[id],events[num]);
                  UserService.getUser($rootScope.SERVER_LOCATION,events[num].owner).success(function(response){
                    events[num].owner_profile = response.extra;
                  }).error(function(res){
          UtilService.hideLoad();
        });
                  UtilService.hideLoad();
              }).error(function(res){
          UtilService.hideLoad();
        });
            }
        }

        this.getRoster = function(base_url,userId){
          var url = base_url+"users/"+userId+"/roster";
          return $http.get(url);
        }

        this.getRosterDetail = function(base_url,userId,rosterDetails){
          var self = this;
          this.getRoster(base_url,userId).success(function(res){
            var roster = res.extra;

            if (roster === "none"){
              return;
            }

            self.parseEventIds(self,roster,base_url,rosterDetails);
          }).error(function(res){
          UtilService.hideLoad();
        });
        }

        this.parseEventIds = function(self,roster,base_url,rosterDetails){
            for (id in roster){
              self.getEvent(base_url,roster[id]).success(function(response){
                self.parseEvent(self,response,rosterDetails);
              }).error(function(res){
          UtilService.hideLoad();
        });
            }
        }

        this.parseCompletedIds = function(self,roster,base_url,rosterDetails){
          for (id in roster){
              self.getCompletedEvent(base_url,roster[id]).success(function(response){
                self.parseEvent(self,response,rosterDetails);
              }).error(function(res){
          UtilService.hideLoad();
        });
            }
        }

        this.getCompletedEvent = function(base_url,eventId){
          return $http.get(base_url+endpoint+"expired/"+eventId);
        }

        this.parseEvent = function(self,response,rosterDetails){
            var eventDetail = response.extra;
                if (eventDetail && eventDetail !== "none"){
                  eventDetail.time = self.getTimeDifference(eventDetail.date);
                  eventDetail.canJoin = self.isParticipant($rootScope.user_id,eventDetail);
                  eventDetail.isOwner = self.isOwner($rootScope.user_id,eventDetail);
                  UserService.getUser($rootScope.SERVER_LOCATION,eventDetail.owner).success(function(owner){
                    eventDetail.owner_profile = owner.extra;
                  }).error(function(res){
          UtilService.hideLoad();
        });
                  rosterDetails.push(eventDetail);
                }
        }

        this.getCompletedEvents = function(base_url,userId,completed){
          var self = this;
          UserService.getCompleted(base_url,userId).success(function(res){
            var roster = res.extra;
            if(roster === "none"){
              return;
            }

            self.parseCompletedIds(self,roster,base_url,completed);
          }).error(function(res){
          UtilService.hideLoad();
        });
        }
	}]);