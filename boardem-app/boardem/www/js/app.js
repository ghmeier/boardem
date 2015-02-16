angular.module('starter', ['ionic', 'starter.controllers'])

.run(function($ionicPlatform, $rootScope,$http) {
  $http.defaults.headers.common["Access-Control-Allow-Origin"] = "http://localhost:8100";

  $ionicPlatform.ready(function() {
	 $rootScope.SERVER_LOCATION = "http://localhost:8080/";
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if (window.cordova && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
    }
    if (window.StatusBar) {
      // org.apache.cordova.statusbar required
      StatusBar.styleDefault();
    }
  });
	
	var dateObject = new Date();
	$rootScope.days =
	$rootScope.daysArray = ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'];
	$rootScope.monthsArray = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
	//Get date in numbers
  $rootScope.month = dateObject.getMonth();
	$rootScope.day = dateObject.getDate();
	$rootScope.year = dateObject.getFullYear();
	//Get date in string format
	$rootScope.monthString = $rootScope.monthsArray[dateObject.getMonth()];
	$rootScope.dayString = $rootScope.daysArray[dateObject.getDay()];
	
	$rootScope.locationSearch = "1";
	$rootScope.gameType = "all";
	
	$rootScope.changeDate = function(direction) {
			var dateObject = new Date($rootScope.year, $rootScope.month, $rootScope.day);
      if(direction == 1 || direction == "1") {
				dateObject.setDate(dateObject.getDate()+1);
			} else {
				dateObject.setDate(dateObject.getDate()-1);
			}
			
			//Get date in numbers
			$rootScope.month = dateObject.getMonth();
			$rootScope.day = dateObject.getDate();
			$rootScope.year = dateObject.getFullYear();
			//Get date in string format
			$rootScope.monthString = $rootScope.monthsArray[dateObject.getMonth()];
			$rootScope.dayString = $rootScope.daysArray[dateObject.getDay()];
	}
	
})

.config(function($stateProvider, $urlRouterProvider,$ionicConfigProvider,$httpProvider) {
  $stateProvider
  .state('auth',{
    url: "/auth",
    templateUrl: "templates/auth.html",
    controller: 'AuthCtrl'
  })
  .state('login',{
    url: "/login",
    abstract: true,
    templateUrl: "templates/login.html",
    controller:"loginCtrl"
  })
  .state('login.signin',{
    url: "/signin",
    views:{
      'signinContent':{
        templateUrl:"templates/signin.html",
        controller:"signinCtrl"
      }
    }
  })
  .state('login.signup',{
    url: "/signup",
    views:{
      'signupContent':{
        templateUrl:"templates/signup.html",
        controller:"signupCtrl"
      }
    }
  })
  .state('app', {
    url: "/app",
    abstract: true,
    templateUrl: "templates/menu.html",
    controller: 'MenuCtrl'
  })

  .state('app.search', {
    url: "/search",
    views: {
      'menuContent': {
        templateUrl: "templates/search.html"
      }
    }
  })

  .state('app.browse', {
    url: "/browse",
    views: {
      'menuContent': {
        templateUrl: "templates/browse.html"
      }
    }
  })
    .state('app.events', {
      url: "/events",
      views: {
        'menuContent': {
          templateUrl: "templates/events.html",
          controller: 'eventsCtrl'
        }
      }
    })

  .state('app.single', {
    url: "/events/:playlistId",
    views: {
      'menuContent': {
        templateUrl: "templates/event.html",
        controller: 'eventCtrl'
      }
    }
  })
  $ionicConfigProvider.views.maxCache(5);
  $ionicConfigProvider.tabs.position('bottom');
  $httpProvider.defaults.headers.common["Access-Control-Allow-Origin"] = "http://localhost:8100";

  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/auth');
});