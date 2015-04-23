/*-----------------------------------------------------
								UTIL SERVICE
-----------------------------------------------------*/
appCtrl.service("UtilService",['$http','$ionicPopup','$ionicLoading',function($http,$ionicPopup,$ionicLoading){

	this.popup = function(title,template){
        $ionicPopup.alert({
          title: title,
          template: template
        });
        return;
	};

    this.checkBadges = function(res){
        if (res.badge && res.badge.length > 0){
            console.log(res.badge);
            for (i in res.badge){
                this.popup("Earned "+res.badge[i].name+"!",res.badge[i].congrats);
            }
        }
    }

    this.showLoad = function(){
        $ionicLoading.show({
          template: 'Loading...',
          noBackdrop: true
        });
    }

    this.hideLoad = function(){
        $ionicLoading.hide();
    }
}]);