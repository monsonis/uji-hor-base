Ext.define('HOR.controller.ControllerFiltroAsignacionAulas',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCentros', 'StoreEstudios', 'StoreCursos' ],
    model : [ 'Centro', 'Estudio', 'Semestre', 'Curso' ],
    refs : [    
    {
        selector : 'panelAulas',
        ref : 'panelAulas'
    } , {
    	selector : 'panelAulas filtroAsignacionAulas',
    	ref : 'filtroAsignacionAulas'
    } ],
    
    init : function()
    {
        this.control(
        {
            'panelAulas filtroAsignacionAulas combobox[name=centro]' :
            {
                select : this.onCentroSelected,
            },

            'panelAulas filtroAsignacionAulas combobox[name=estudio]' :
            {
                select : this.onEstudioSelected,
            },

            'panelAulas filtroAsignacionAulas combobox[name=semestre]' :
            {
                select : this.onSemestreSelected
            }
        });
    },
    
    onCentroSelected : function(combo, records)
    {
    	   	
    	var combo_estudios = this.getFiltroAsignacionAulas().down('combobox[name=estudio]');
    	combo_estudios.setDisabled(false);
    	combo_estudios.clearValue();
    	
    	var estudios_store = this.getStoreEstudiosStore();
    	estudios_store.load({
    		params : {
    			centroId : records[0].get('id')
    		},
    		scope : this
    	});
    	
    	var combo_semestre = this.getFiltroAsignacionAulas().down('combobox[name=semestre]');
    	combo_semestre.setDisabled(true);
    	combo_semestre.clearValue();
    	    	
        fixLoadMaskBug(estudios_store, combo_estudios);
    	
    },
    
    onEstudioSelected : function(combo, records)
    {
    	var combo_semestre = this.getFiltroAsignacionAulas().down('combobox[name=semestre]');
    	combo_semestre.setDisabled(false);
    	combo_semestre.select(combo_semestre.getStore().data.items[0]);
    	
        fixLoadMaskBug(combo_semestre.getStore(), combo_semestre );    	
    	
    	this.actualizarDatosArbol();
    },
    
    onSemestreSelected : function(combo, records)
    {
    	this.actualizarDatosArbol();
    },
    
    actualizarDatosArbol : function() {
//        Ext.MessageBox.alert('Recarga', 'Ahora se recargarán los datos del árbol');
    	console.log('Ahora se recargarán los datos del árbol');
    }
    
});