appCtrl.service("ExperienceService",['$rootScope','$http',function($rootScope,$http){

	this.addToUserXP = function(base_url, userid, xp_amount){

		return $http.post(base_url+"exp/"+userid+"?exp"+xp_amount);

	}
	
	this.updateUserXPInfo = function(base_url, userid){
		
		var experience = parseInt($http.get(base_url+"exp/"+userid));
		$rootScope.xp = experience;
		if(experience < 1000) {
			$rootScope.level = 0;
			//return {experience, 0};
		} else if(experience < 2000) {
			$rootScope.level = 1;
			//return {experience, 1};
		} else if(experience < 4000) {
			$rootScope.level = 2;
			//return {experience, 2};
		} else if(experience < 7000) {
			$rootScope.level = 3;
			//return {experience, 3};
		} else if(experience < 11000) {
			$rootScope.level = 4;
			//return {experience, 4};
		} else if(experience < 16000) {
			$rootScope.level = 5;
			//return {experience, 5};
		} else if(experience < 21000) {
			$rootScope.level = 6;
			//return {experience, 6};
		} else if(experience < 28000) {
			$rootScope.level = 7;
			//return {experience, 7};
		} else if(experience < 36000) {
			$rootScope.level = 8;
			//return {experience, 8};
		} else if(experience < 45000) {
			$rootScope.level = 9;
			//return {experience, 9};
		} else if(experience < 55000) {
			$rootScope.level = 10;
			//return {experience, 10};
		}
	}
}]);