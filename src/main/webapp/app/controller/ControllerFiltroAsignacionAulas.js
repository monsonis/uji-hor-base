Ext.define('HOR.controller.ControllerFiltroAsignacionAulas',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCentros', 'StoreEstudios', 'StoreCursos', 'TreeStoreAulas', 'StoreAulasAsignadas' ],
    model : [ 'Centro', 'Estudio', 'Semestre', 'Curso', 'AulaPlafinicacion' ],
    refs : [
    {
        selector : 'panelAulas',
        ref : 'panelAulas'
    },
    {
        selector : 'panelAulas filtroAsignacionAulas',
        ref : 'filtroAsignacionAulas'
    },
    {
        selector : 'treePanelAulas',
        ref : 'treePanelAulas'
    },
    {
        selector : 'gridAulas',
        ref : 'gridAulas'
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
            
            'treePanelAulas' :
            {
            	itemdblclick : this.anyadirAula,
            },

            'panelAulas filtroAsignacionAulas combobox[name=semestre]' :
            {
                select : this.onSemestreSelected
            },
            'treePanelAulas button[name=anyadir]' :
            {
                click : this.anyadirAula
            },
            'gridAulas button[name=borrar]' :
            {
                click : this.borrarAula
            }

        });
    },    
    onCentroSelected : function(combo, records)
    {

        var combo_estudios = this.getFiltroAsignacionAulas().down('combobox[name=estudio]');
        combo_estudios.setDisabled(false);
        combo_estudios.clearValue();

        var estudios_store = this.getStoreEstudiosStore();
        estudios_store.load(
        {
            params :
            {
                centroId : records[0].get('id')
            },
            scope : this
        });

        var combo_semestre = this.getFiltroAsignacionAulas().down('combobox[name=semestre]');
        combo_semestre.setDisabled(true);
        combo_semestre.clearValue();

        this.actualizarDatosArbolAulas();
        this.getTreePanelAulas().setDisabled(true);
        this.getGridAulas().setDisabled(true);

        this.limpiaDatosGridAulas();

        fixLoadMaskBug(estudios_store, combo_estudios);

    },

    onEstudioSelected : function(combo, records)
    {
        var combo_semestre = this.getFiltroAsignacionAulas().down('combobox[name=semestre]');
        combo_semestre.setDisabled(false);
        combo_semestre.select(combo_semestre.getStore().data.items[0]);

        this.actualizarDatosGridAsignaciones();
        this.getTreePanelAulas().setDisabled(false);
        this.getGridAulas().setDisabled(false);

        this.actualizarDatosGridAsignaciones();

        fixLoadMaskBug(combo_semestre.getStore(), combo_semestre);

    },

    onSemestreSelected : function(combo, records)
    {
        this.actualizarDatosGridAsignaciones();
    },

    actualizarDatosArbolAulas : function()
    {
        var tree = this.getTreePanelAulas();
        var centroId = this.getFiltroAsignacionAulas().down("combobox[name=centro]").getValue();

        tree.getStore().load(
        {
            url : '/hor/rest/aula/centro/' + centroId + '/tree'
        });

    },

    actualizarDatosGridAsignaciones : function()
    {
        var estudioId = this.getFiltroAsignacionAulas().down("combobox[name=estudio]").getValue();
        var semestreId = this.getFiltroAsignacionAulas().down("combobox[name=semestre]").getValue();
        var grid = this.getGridAulas();
        grid.getStore().load(
        {
            url : '/hor/rest/aula/estudio/' + estudioId,
            params :
            {
                semestreId : semestreId,
                cursoId : '0'
            }
        });
    },

    borrarAula : function()
    {
        var listaSeleccion = this.getGridAulas().getSelectionModel().getSelection();
        if (listaSeleccion.length > 0)
        {
        	 
            var gridStore = this.getGridAulas().getStore();

            var ref = this;
            gridStore.remove(listaSeleccion);
            gridStore.sync(
            {
                failure : function()
                {
                	ref.actualizarDatosGridAsignaciones();
                }
            });
        }
    },

    anyadirAula : function()
    {
        var listaSeleccion = this.getTreePanelAulas().getSelectionModel().getSelection();
        if (listaSeleccion.length > 0)
        {
            var estudioId = this.getFiltroAsignacionAulas().down("combobox[name=estudio]").getValue();
            var semestreId = this.getFiltroAsignacionAulas().down("combobox[name=semestre]").getValue();

            var gridStore = this.getGridAulas().getStore();

            var itemsParaAnyadir = new Array();
            for (index in listaSeleccion){
            	var item = listaSeleccion[index];
            	if (item.get("leaf") === true){
	            	 var aulaAsignada = Ext.ModelManager.create(
	            	            {
	            	                estudioId : estudioId,
	            	                cursoId : '0',
	            	                nombre : item.get("text"),
	            	                estudioId : estudioId,
	            	                aulaId : item.get("id"),
	            	                semestreId : semestreId
	            	            }, "HOR.model.AulaPlanificacion");
	
	            	 itemsParaAnyadir.push(aulaAsignada);  
            	}
            }
            
            var ref = this;
            if (itemsParaAnyadir.length > 0 ){
	            gridStore.add(itemsParaAnyadir);
	            gridStore.sync(
	            {
	                failure : function()
	                {
	                	ref.actualizarDatosGridAsignaciones();
	                }
	            });
            }
        }
    },

    limpiaDatosGridAulas : function()
    {
        var grid = this.getGridAulas();
        grid.getStore().removeAll();
    }
});