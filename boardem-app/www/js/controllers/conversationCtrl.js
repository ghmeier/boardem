appCtrl.controller('ConversationCtrl',function($rootScope,$window,$stateParams,$ionicPopup,$http, $scope, $state,UserService){
    $scope.messages = [];
    $scope.messageId = "";
    $scope.pics = {};
    $scope.data = {comment : ""};

    $scope.getMessages = function(){
        $scope.messages = [];
        $scope.messageId = $stateParams.mid;
        UserService.getMessageDetails($rootScope.SERVER_LOCATION,$scope.messageId,$scope.messages,$scope.pics);
    }

    $scope.comment = function(){
        UserService.postMessage($rootScope.SERVER_LOCATION,$stateParams.mid,$rootScope.user_id,$scope.data.comment).success(function(res){
            $scope.data.comment = '';
            $scope.getMessages();
        })
    }

});