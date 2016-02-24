//CONTROLLERS

companyApp.controller('companyListController', ['$scope', '$http','$log', 'companyService', function($scope, $http, $log, companyService) {

	$scope.page = companyService.page;
	
	//Listens to change for Pagination
	$scope.$watchGroup(['page.num','page.size'], function(newValues, oldValues, scope){
		companyService.page = $scope.page;
		$scope.getCompanyListApi();
	});

	$scope.newOwnerRow = function(){
		var newOwner = { firstName : 'first name...', 
						 lastName: 'last name...'};
		return newOwner;
	};
	
	$scope.getCompanyListResult = null;
	
	//Ajax Call to fetch Company List
	$scope.getCompanyListApi = function(){
		$http({
			method: 'GET',
			url: '/companyapp/getCompanyList/?pageNum='+$scope.page.num+'&pageSize='+$scope.page.size
		}).then(function successCallback(response) {
			$log.info(response.data);
			if(response.data.messageCode === undefined){
				$scope.getCompanyListResult = response.data;
				$scope.errorMessage = undefined;
			}else{
				$scope.getCompanyListResult = {};
				$scope.errorMessage = response.data.message;
			}
		}, function errorCallback(response) {
			$log.error('Get Company List fetch failed in Error.');
		});
	};
	
	//At the time of Load
	$scope.getCompanyListApi();

	//Ajax call to fetch Company Details
	$scope.showMoreInfo = function(index){
		companyInfo = $scope.getCompanyListResult[index];
		if(companyInfo.showMoreFlag === true){
			companyInfo.showMoreFlag = false;
		}else{
			delete companyInfo.showMoreFlag;
			$http({
				method: 'POST',
				url: '/companyapp/getCompanyDetails/',
				data: companyInfo	
			}).then(function successCallback(response) {
				$log.info(response.data);
				companyInfo = response.data;
				companyInfo.showMoreFlag = true;
				$scope.getCompanyListResult[index] = companyInfo;
			}, function errorCallback(response) {
				$log.error('Get Company List fetch failed in Error.');
			});
		}
	};
	
	//Ajax call to Submit CompanyInfo object for Updates
	$scope.updateCompanyInfo = function(index){
		companyInfo = $scope.getCompanyListResult[index];
		delete companyInfo.showMoreFlag;
		$http({
			method: 'POST',
			url: '/companyapp/updateCompany/',
			data: companyInfo
		}).then(function successCallback(response) {
			$log.info(response.data);
			if(response.data.messageCode === undefined){
				companyInfo = response.data;$scope.getCompanyListResult[index] = companyInfo;
				$scope.errorMessage = undefined;
			}else{
				$scope.getCompanyListResult = {};
				$scope.errorMessage = response.data.message;
			}
			companyInfo.showMoreFlag = true;
		}, function errorCallback(response) {
			$log.error('Get Company List fetch failed in Error.');
		});
		
	}; 
	
	//Handles on Add-Owner. Adds an object to beneficialOwners of  
	//underlying Company object which is reflected in view through Scoped-MVC
	$scope.addOwner = function(index){

		companyInfo = $scope.getCompanyListResult[index];
		beneficialOwners = companyInfo.beneficialOwners;

		if(beneficialOwners === undefined || beneficialOwners === null){
			$scope.getCompanyListResult[index].beneficialOwners = [$scope.newOwnerRow()];
		}else{
			$scope.getCompanyListResult[index].beneficialOwners.push($scope.newOwnerRow());
		}

	};

}]);