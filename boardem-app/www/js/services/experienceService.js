appCtrl.service("ExperienceService",["$http",function($http){

	this.addToUserXP = function(base_url, userid, xp_amount){

		return $http.post(base_url+"exp/"+userid+"?exp"+xp_amount);

	}
	
	this.getUserXP = function(base_url, userid){

		return $http.get(base_url+"exp/"+userid);

	}
	
	this.getUserLevel = function(base_url, userid){
		var experience = parseInt($http.get(base_url+"exp/"+userid));
		
		if(experience < 1000) {
			return 0;
		} else if(experience < 2000) {
			return 1;
		} else if(experience < 4000) {
			return 2;
		} else if(experience < 7000) {
			return 3;
		} else if(experience < 11000) {
			return 4;
		} else if(experience < 16000) {
			return 5;
		} else if(experience < 21000) {
			return 6;
		} else if(experience < 28000) {
			return 7;
		} else if(experience < 36000) {
			return 8;
		} else if(experience < 45000) {
			return 9;
		} else if(experience < 55000) {
			return 10;
		}
	}
	
}]);