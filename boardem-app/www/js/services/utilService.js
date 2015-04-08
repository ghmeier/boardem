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

    this.checkBadges = function(res){
        if (res.badge && res.badge.length > 0){
            console.log(res.badge);
            for (i in res.badge){
                this.popup("Earned "+res.badge[i].name+"!",res.badge[i].congrats);
            }
        }
    }

}]);