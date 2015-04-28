appCtrl.service('BadgeService', ['$ionicPopup','$rootScope','$http','RestService','UtilService', function ($ionicPopup,$rootScope,$http,RestService,UtilService) {

    var endpoint = "badges/"

    this.getBadges = function(base_url,badges,earned){
        var self = this;
        UtilService.showLoad();
        $http.get(base_url+endpoint).success(function(res){
            var badge_ids = res.extra;
            badge_ids.sort();
            for (id in badge_ids){
                self.getBadgeDetail(base_url,badge_ids[id]).success(function(r){
                    r.extra.opacity = .5;
                    for (i in earned){
                        if (earned[i].id === r.extra.id){
                            r.extra.opacity = 1;
                            break;
                        }
                    }
                    badges.push(r.extra);
                }).error(function(res){
                    UtilService.hideLoad();
                });
            }
            UtilService.hideLoad();
        }).error(function(res){
                    UtilService.hideLoad();
                });
    }

    this.getBadgeDetail = function(base_url,badge_id){
        return $http.get(base_url+endpoint+badge_id);
    }
}]);
