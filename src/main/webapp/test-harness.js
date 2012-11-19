var Harness = Siesta.Harness.Browser.ExtJS;

Harness.configure({
	title : 'Awesome Test Suite',
	loaderPath : {
		'HOR' : 'app'
	},

	preload : [ '/hor/sinon/sinon-1.5.0.js', '/hor/sinon/sinon-init.js',
	// 'http://cdn.sencha.io/ext-4.1.0-gpl/resources/css/ext-all.css',
	'/hor/Ext/ext-all-debug.css',
			'/hor/extensible-1.5.1/resources/css/extensible-all.css',

			'/hor/css/custom.css',
			// 'http://cdn.sencha.io/ext-4.1.0-gpl/ext-debug.js',
			'/hor/Ext/ext-all-debug-w-comments.js',
			// 'http://cdn.sencha.io/ext-4.1.0-gpl/locale/ext-lang-ca.js',
			'/hor/Ext/ext-lang-ca.js',
			'/hor/extensible-1.5.1/lib/extensible-all-debug.js',
			'/hor/extensible-1.5.1/src/locale/extensible-lang-ca.js',
	// '/hor/app/Application.js'
	]
});

Harness.start({
	group : "Dates Curs Academic",
	items : [ 'tests/010-DatesCursAcademic/010_sanity.t.js',
			'tests/010-DatesCursAcademic/020_semestredetallemodel.t.js',
			'tests/010-DatesCursAcademic/030_panel_semestre.t.js', ]
}, {
	group : "Asignacio d'aules a estudis",
	items : [ 'tests/020-AsignacioAulesEstudis/010_sanity.t.js',
	         // 'tests/020-AsignacioAulesEstudis/020_view_filtroasignacionaulas.t.js',
			 ]
}, {
	group : 'Application',

	// need to set the `preload` to empty array - to avoid the double loading of
	// dependencies
	preload : [],

	items : [ {
		hostPageUrl : 'index.html',
		url : 'tests/900-Aplicacion/900-010_app.t.js'
	},
	 {
		hostPageUrl : 'index.html',
		url : 'tests/900-Aplicacion/900-020_assignacioaules.t.js'
	}
	]
}

);