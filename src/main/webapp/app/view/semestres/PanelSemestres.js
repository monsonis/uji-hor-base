Ext.define('HOR.view.semestres.PanelSemestres', {
	extend : 'Ext.panel.Panel',
	title : 'Dates curs acadèmic',
	requires : [],
	alias : 'widget.panelSemestres',
	closable : true,
	layout : {
		type : 'vbox',
		align : 'center',
		padding : 10
	},

	items : [ {
		xtype : 'grid',
		store : 'StoreSemestreDetalles',
		align : 'center',
        style: 'text-align:center;',
        disableSelection : true,
        sortableColumns: false,
		width : 650,
		columns : [ {
			text : 'Tipo estudio',
			dataIndex : 'nombreTipoEstudio',
			menuDisabled : true,
			flex : 1
		}, {
			text : 'Nombre Semestre',
			dataIndex : 'nombreSemestre',
			menuDisabled : true			
		}, {
			text : 'Inicio docencia',
			dataIndex : 'fecha_inicio',
			menuDisabled : true,
			renderer: function(value) {
				return Ext.Date.format(value, "d/m/Y");
			}
		}, {
			text : 'Fin docencia',
			dataIndex : 'fecha_fin',
			menuDisabled : true,
			renderer: function(value) {
				return Ext.Date.format(value, "d/m/Y");
			}
		}, {
			text : 'Inicio exámenes',
			dataIndex : 'fecha_examenes_inicio',
			menuDisabled : true,
			renderer: function(value) {
				return Ext.Date.format(value, "d/m/Y");
			}
		}, {
			text : 'Fin exámenes',
			dataIndex : 'fecha_examenes_fin',
			menuDisabled : true,
			renderer: function(value) {
				return Ext.Date.format(value, "d/m/Y");
			}
		} ]
	}

	]

});