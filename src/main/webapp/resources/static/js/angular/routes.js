//ROUTES
companyApp.config(function ($routeProvider) {
	// Defines default route 
	// can be built further to provide SPA 
	// like HTML client.
	$routeProvider.when('/', {
		templateUrl: '/companyapp/resources/pages/companyList.htm',
		controller: 'companyListController'
	});

});