/*-----------------------------------------------------
								UTIL SERVICE
-----------------------------------------------------*/
appCtrl.service("UtilService",['$http','$ionicPopup',function($http,$ionicPopup){

	this.popup = function(title,template){
        $ionicPopup.alert({
          title: title,
          template: template
        }); 
        return;
	};

}]);