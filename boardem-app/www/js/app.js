var myApp = angular.module('starter', ['ionic', 'starter.controllers','firebase'])

.run(function($ionicPlatform, $rootScope,$http,$firebaseAuth,$window,UserService) {
  //$http.defaults.headers.common["Access-Control-Allow-Origin"] = "http://localhost:8100";

  $ionicPlatform.ready(function() {
	 $rootScope.SERVER_LOCATION = "https://boardem.herokuapp.com/";
   $rootScope.user_id = $window.localStorage['id'];
   $rootScope.events = [];
	 $rootScope.shelfGames = [];
   $rootScope.userBadges = [];
   $rootScope.roster = [];
	 $rootScope.xp_info = [];

   UserService.getShelf($rootScope.SERVER_LOCATION,$rootScope.user_id,$rootScope.shelfGames)
   UserService.getBadges($rootScope.SERVER_LOCATION,$rootScope.user_id,$rootScope.userBadges);
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

	$rootScope.events = [];
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

  .state('app.profile',{
    url: "/profile/:profileId",
    views: {
      'menuContent':{
        templateUrl: "templates/profile.html",
        controller: 'profileCtrl'
      }
    }
  })

  .state('app.browse', {
    url: "/browse",
    views: {
      'menuContent': {
        templateUrl: "templates/browse.html",
        controller:"BrowseCtrl"
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
    url: "/events/:eventId",
    cache: false,
    views: {
      'menuContent': {
        templateUrl: "templates/event.html",
        controller: 'eventCtrl'
      }
    }
  })
  .state('app.completed', {
    url: "/completed_events/:eventId",
    views: {
      'menuContent': {
        templateUrl: "templates/completed_event.html",
        controller: 'completeEventCtrl'
      }
    }
  })
  .state('app.games', {
    url: "/games",
    views: {
      'menuContent': {
        templateUrl: "templates/games.html",
        controller: 'GamesCtrl'
      }
    }
  })
  .state('app.game', {
    url: "/game/:gameId",
    views: {
      'menuContent': {
        templateUrl: "templates/game.html",
        controller: 'GameCtrl'
      }
    }
  })
	.state('app.create', {
    url: "/event/create",
    views: {
      'menuContent': {
        templateUrl: "templates/create-event.html",
        controller: 'createEventCtrl'
      }
    }
  })
	.state('app.edit', {
    url: "/event/edit/:eventId",
    views: {
      'menuContent': {
        templateUrl: "templates/edit-event.html",
        controller: 'editEventCtrl'
      }
    }
  })
  .state('app.roster', {
    url: "/roster",
    views: {
      'menuContent': {
        templateUrl: "templates/roster.html",
        controller: 'rosterCtrl'
			}
    }
  })
	.state('app.shelf', {
    url: "/shelf",
    views: {
      'menuContent': {
        templateUrl: "templates/shelf.html",
        controller: 'shelfCtrl'
      }
    }
  })
  .state('app.messages', {
    url: "/messages",
    views: {
      'menuContent': {
        templateUrl: "templates/messages.html",
        controller: 'MessagesCtrl'
      }
    }
  })
  .state('app.conversation', {
    url: "/messages/:mid",
    views: {
      'menuContent': {
        templateUrl: "templates/conversation.html",
        controller: 'ConversationCtrl'
      }
    }
  })
  .state('app.badges', {
    url: "/badges",
    views: {
      'menuContent': {
        templateUrl: "templates/badges.html",
        controller: 'BadgesCtrl'
      }
    }
  })
  .state('app.contacts', {
    url: "/contacts",
    views: {
      'menuContent': {
        templateUrl: "templates/contacts.html",
        controller: 'ContactsCtrl'
      }
    }
  });
  $ionicConfigProvider.views.maxCache(5);
  $ionicConfigProvider.tabs.position('bottom');

  $urlRouterProvider.otherwise('/auth');
});