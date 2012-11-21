Ext.define('HOR.controller.ControllerFiltroAsignacionAulas',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCentros', 'StoreEstudios', 'StoreCursos', 'TreeStoreAulas', 'StoreAulasAsignadas' ],
    model : [ 'Centro', 'Estudio', 'Semestre', 'Curso', 'Aula' ],
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

            'panelAulas filtroAsignacionAulas combobox[name=semestre]' :
            {
                select : this.onSemestreSelected
            },
            'menuSuperior menuitem[action=asignacion-aulas]' :
            {
                click : this.limpiaDatosArbol
            }
        });
    },

    onCentroSelected : function(combo, records)
    {

        this.limpiaDatosArbol();

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

        fixLoadMaskBug(estudios_store, combo_estudios);

    },

    onEstudioSelected : function(combo, records)
    {
        this.limpiaDatosArbol();

        var combo_semestre = this.getFiltroAsignacionAulas().down('combobox[name=semestre]');
        combo_semestre.setDisabled(false);
        combo_semestre.select(combo_semestre.getStore().data.items[0]);

        fixLoadMaskBug(combo_semestre.getStore(), combo_semestre);

        this.actualizarDatosArbol();
    },

    onSemestreSelected : function(combo, records)
    {
        this.actualizarDatosArbol();
    },
    
    actualizarDatosArbol : function()
    {
        var tree = this.getTreePanelAulas();
        tree.setDisabled(false);
        var centroId = this.getFiltroAsignacionAulas().down("combobox[name=centro]").getValue();
        var estudioId = this.getFiltroAsignacionAulas().down("combobox[name=estudio]").getValue();
        tree.getStore().load({ url: '/hor/rest/aula/centro/' + centroId + '/tree' }).load();
        
        var grid = this.getGridAulas();
        grid.setDisabled(false);
    },

    limpiaDatosArbol : function()
    {
        var tree = this.getTreePanelAulas();
        tree.setDisabled(true);
        tree.getRootNode().removeAll();

        var grid = this.getGridAulas();
        grid.setDisabled(true);
    }
});