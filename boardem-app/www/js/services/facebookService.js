appCtrl.service("FacebookService",['$http',function($http){
    var key = "access_token=906099946120368|3b0cb9b7d2bacdb4681a2c66a5b31722";
    var url = "https://graph.facebook.com/v2.3/";
    var nextPage = "";
    var offset = 0;

    this.getUser = function(id){
        $http.get(url+id+"/?"+key).success(function(res){
            console.log(res);
        });
    }

    this.getFriends = function(id,token,fb){
        var getUrl = url+id+"/invitable_friends?access_token="+token+"&limit=25&after="+nextPage;

        return $http.get(getUrl).success(function(res){
            console.log(res);
            if (nextPage === res.paging.cursors.after){
                return;
            }
            nextPage = res.paging.cursors.after;
            for (id in res.data){
                fb.push(res.data[id]);
            }
        });
    }

}]);