Ext.define('HOR.controller.ControllerFiltroCalendario',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreEstudios', 'StoreCursos', 'StoreSemestres', 'StoreGrupos' ],
    model : [ 'Estudio', 'Curso', 'Semestre', 'Grupo' ],
    refs : [
    {
        selector : 'panelHorarios filtroGrupos',
        ref : 'filtroGrupos'
    },
    {
        selector : 'selectorGrupos',
        ref : 'selectorGrupos'
    },
    {
        selector : 'panelCalendario',
        ref : 'panelCalendario'
    } ],

    init : function()
    {
        this.control(
        {
            'panelHorarios filtroGrupos combobox[name=estudio]' :
            {
                select : this.onTitulacionSelected,
            },

            'panelHorarios filtroGrupos combobox[name=curso]' :
            {
                select : this.onCursoSelected,
            },

            'panelHorarios filtroGrupos combobox[name=semestre]' :
            {
                select : this.onSemestreSelected
            }
        });
    },

    onTitulacionSelected : function(combo, records)
    {
        this.getFiltroGrupos().down('combobox[name=curso]').clearValue();
        this.getFiltroGrupos().down('combobox[name=semestre]').clearValue();
        this.getFiltroGrupos().down('combobox[name=grupo]').clearValue();
        this.getFiltroGrupos().down('button[name=intervaloHorario]').hide();

        this.getPanelCalendario().limpiaCalendario();
        this.getSelectorGrupos().limpiaGrupos();

        this.getStoreSemestresStore().removeAll();
        this.getStoreGruposStore().removeAll();

        var store = this.getStoreCursosStore();

        store.load(
        {
            params :
            {
                estudioId : records[0].get('id')
            },
            scope : this
        });

        fixLoadMaskBug(store, this.getFiltroGrupos().down('combobox[name=curso]'));
    },

    onCursoSelected : function(combo, records)
    {
        this.getFiltroGrupos().down('combobox[name=semestre]').clearValue();
        this.getFiltroGrupos().down('combobox[name=grupo]').clearValue();
        this.getFiltroGrupos().down('button[name=intervaloHorario]').hide();

        this.getPanelCalendario().limpiaCalendario();
        this.getSelectorGrupos().limpiaGrupos();

        this.getStoreGruposStore().removeAll();

        var estudio = this.getFiltroGrupos().down('combobox[name=estudio]').getValue();

        var store = this.getStoreSemestresStore();

        store.load(
        {
            params :
            {
                estudioId : estudio,
                cursoId : records[0].data.curso
            },
            scope : this
        });

        fixLoadMaskBug(store, this.getFiltroGrupos().down('combobox[name=semestre]'));
    },

    onSemestreSelected : function(combo, records)
    {
        this.getFiltroGrupos().down('combobox[name=grupo]').clearValue();
        this.getFiltroGrupos().down('button[name=intervaloHorario]').hide();

        this.getPanelCalendario().limpiaCalendario();
        this.getSelectorGrupos().limpiaGrupos();

        var estudio = this.getFiltroGrupos().down('combobox[name=estudio]').getValue();
        var curso = this.getFiltroGrupos().down('combobox[name=curso]').getValue();

        var store = this.getStoreGruposStore();

        store.load(
        {
            params :
            {
                estudioId : estudio,
                cursoId : curso,
                semestreId : records[0].data.semestre
            },
            scope : this
        });

        fixLoadMaskBug(store, this.getFiltroGrupos().down('combobox[name=grupo]'));
    },

});