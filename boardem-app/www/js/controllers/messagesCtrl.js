appCtrl.controller('MessagesCtrl',function($rootScope,$window,$ionicPopup,$http, $scope, $ionicModal,$state,UserService,FacebookService,UtilService){
    $scope.message_details = [];
    $scope.users = [];
    $scope.conv_users = [];

    $ionicModal.fromTemplateUrl('contact-modal.html', {
        scope: $scope,
        animation: 'slide-in-up'
      }).then(function(modal) {
        $scope.modal = modal;
      });

    $scope.getMessages = function(){
        $scope.message_details = [];
        UserService.getUserMessages($rootScope.SERVER_LOCATION,$rootScope.user_id,$scope.message_details);
    }

    $scope.toMessages = function(id){
        window.location.href= "#/app/messages/"+id;
    }

    $scope.showAdd = function(){
        $scope.users = UserService.getUserContacts($rootScope.SERVER_LOCATION,$rootScope.user_id);
        $scope.modal.show();
    }

    $scope.startConversation = function(){
        var users = [];
        for (id in $scope.conv_users){
            users.push($scope.conv_users[id].facebook_id);
        }
        users.push($rootScope.user_id);
        UserService.createConversation($rootScope.SERVER_LOCATION,users).success(function(res){
            if (res.code == 0){
                $scope.getMessages();
                $scope.closeAdd();
            }else{
                UtilService.popup("Error",res.message);
            }
        })
    }

    $scope.addUser = function(user){
        if (user.added){
            user.added = false;
            for (id in $scope.conv_users){
                if (user.facebook_id === $scope.conv_users[id].facebook_id){
                    $scope.conv_users.splice(id,1);
                    break;
                }
            }
        }else{
            user.added = true;
            $scope.conv_users.push(user);
        }
    }

    $scope.closeAdd = function(){
        $scope.modal.hide();
        for (id in $scope.conv_users){
            $scope.conv_users[id].added = false;
        }
        $scope.conv_users = [];
    }

});