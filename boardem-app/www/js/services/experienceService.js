appCtrl.service("ExperienceService",['$rootScope','$http',function($rootScope,$http){
	
	this.getXP = function(base_url, userid) {
		
		return $http.get(base_url+"exp/"+userid);
		
	}
	
	this.addToUserXP = function(base_url, userid, xp_amount){
		return $http.post(base_url+"exp/"+userid+"?exp="+xp_amount);
	}
	
	this.updateUserXPInfo = function(base_url, userid, xp_info){
		return this.getXP(base_url, userid).success(function(res){
			var experience = parseInt(res.extra);
			var percent_scope, level_scope;
			//$rootScope.xp = experience;
			if(experience < 1000) {
				percent_scope = (experience / 1000 ) * 100;
				level_scope = 0;
				//return {experience, 0};
			} else if(experience < 2000) {
				percent_scope = (experience / 2000 ) * 100;
				level_scope = 1;
				//return {experience, 1};
			} else if(experience < 4000) {
				percent_scope = (experience / 4000 ) * 100;
				level_scope = 2;
				//return {experience, 2};
			} else if(experience < 7000) {
				percent_scope = (experience / 7000 ) * 100;
				level_scope = 3;
				//return {experience, 3};
			} else if(experience < 11000) {
				percent_scope = (experience / 11000 ) * 100;
				level_scope = 4;
				//return {experience, 4};
			} else if(experience < 16000) {
				percent_scope = (experience / 16000 ) * 100;
				level_scope = 5;
				//return {experience, 5};
			} else if(experience < 21000) {
				percent_scope = (experience / 21000 ) * 100;
				level_scope = 6;
				//return {experience, 6};
			} else if(experience < 28000) {
				percent_scope = (experience / 28000 ) * 100;
				level_scope = 7;
				//return {experience, 7};
			} else if(experience < 36000) {
				percent_scope = (experience / 36000 ) * 100;
				level_scope = 8;
				//return {experience, 8};
			} else if(experience < 45000) {
				percent_scope = (experience / 45000 ) * 100;
				level_scope = 9;
				//return {experience, 9};
			} else if(experience < 55000) {
				percent_scope = (experience / 55000 ) * 100;
				level_scope = 10;
				//return {experience, 10};
			}
			
			xp_info.push(experience);
			xp_info.push(level_scope);
			xp_info.push(percent_scope);
		});
	}
}]);