Ext.define('HOR.controller.ControllerAsignacionAulasForm', {
	extend : 'Ext.app.Controller',
	refs : [ {
		selector : 'formAsignacionAulas',
		ref : 'formAsignacionAulas'
	} ],

	init : function() {
		this.control({
			'formAsignacionAulas button[name=close]' : {
				click : this.cerrarFormulario
			}
		});
	},

	cerrarFormulario : function() {
		var panelCalendario = Ext.ComponentQuery.query('panelCalendario')[0];
		panelCalendario.hideAsignarAulaView();
	}
});