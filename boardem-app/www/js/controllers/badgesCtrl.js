appCtrl.controller('BadgesCtrl',function($rootScope,$window,$ionicPopup,$http, $scope, $state,UserService,BadgeService){
    $scope.badges = [];

    $scope.initBadges = function(){
        BadgeService.getBadges($rootScope.SERVER_LOCATION,$scope.badges,$rootScope.userBadges);
    }

});