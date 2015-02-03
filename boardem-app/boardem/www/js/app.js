
angular.module('starter', ['ionic', 'starter.controllers'])

.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
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
})

.config(function($stateProvider, $urlRouterProvider) {
  $stateProvider
  .state('auth',{
    url: "/auth",
    templateUrl: "templates/auth.html",
    controller: 'AuthCtrl'
  })
  .state('signin',{
    url: "/signin",
    templateUrl:"templates/signin.html",
    controller:"signinCtrl"
  })
  .state('signup',{
    url: "/signup",
    templateUrl:"templates/signup.html",
    controller:"signupCtrl"
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
  });
  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/auth');
});
