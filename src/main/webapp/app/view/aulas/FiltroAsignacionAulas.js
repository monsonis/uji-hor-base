Ext.define('HOR.view.aulas.FiltroAsignacionAulas', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.filtroAsignacionAulas',

	border : false,
	padding : 5,
	closable : false,

	layout : 'anchor',

	items : [ {
		xtype : 'combobox',
		fieldLabel : 'Centres',
		labelWidth : 65,
		store : 'StoreCentros',
		editable : false,
		displayField : 'nombre',
		valueField : 'id',
		name : 'centro',
		anchor : '100%',
	    alias : 'widget.comboCentros'			
	}, {
		xtype : 'combobox',
		lastQuery : '',
		fieldLabel : 'Estudis',
		labelWidth : 65,
		store : 'StoreEstudios',
		lastQuery : '',
		editable : false,
		displayField : 'nombre',
		valueField : 'id',
		name : 'estudio',
		anchor : '100%',
		disabled : true,
		alias : 'widget.comboEstudios'
	},
	{
		xtype : 'combobox',
		lastQuery : '',
		fieldLabel : 'Semestre:',
		labelWidth : 65,
		store: Ext.create('Ext.data.ArrayStore', {
            fields : ['index', 'name'],
            data : [['1', '1'], ['2', '2']]
        }),		editable : false,
		displayField : 'name',
		valueField : 'index',
		name : 'semestre',
		anchor : '20%',
		disabled : true	,
		alias : 'widget.comboSemestre'
	}
	]
});