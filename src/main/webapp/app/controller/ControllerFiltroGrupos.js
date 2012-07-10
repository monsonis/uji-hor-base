Ext.define('HOR.controller.ControllerFiltroGrupos',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreEstudios', 'StoreCursos', 'StoreSemestres', 'StoreGrupos', 'StoreEventos' ],
    model : [ 'Estudio', 'Curso', 'Semestre', 'Grupo' ],
    refs : [
    {
        selector : 'filtroGrupos',
        ref : 'filtroGrupos'
    },
    {
        selector: 'panelHorarios',
        ref: 'panelHorarios'
    }],

    init : function()
    {
        this.control(
        {
            'filtroGrupos > #titulaciones' :
            {
                select : this.onTitulacionSelected,
            },

            'filtroGrupos > #cursos' :
            {
                select : this.onCursoSelected,
            },

            'filtroGrupos > #semestres' :
            {
                select : this.onSemestreSelected
            }
        });
    },

    onTitulacionSelected : function(combo, records)
    {
        this.getFiltroGrupos().down('#cursos').clearValue();
        this.getFiltroGrupos().down('#semestres').clearValue();
        this.getFiltroGrupos().down('#grupos').clearValue();
        
        this.getStoreSemestresStore().removeAll();
        this.getStoreGruposStore().removeAll();

        var store = this.getStoreCursosStore();

        store.load(
        {
            params :
            {
                estudioId : records[0].data.id
            },
            scope : this
        });

        this.fixLoadMaskBug(store, this.getFiltroGrupos().down('#cursos'));
    },

    onCursoSelected : function(combo, records)
    {
        this.getFiltroGrupos().down('#semestres').clearValue();
        this.getFiltroGrupos().down('#grupos').clearValue();

        this.getStoreGruposStore().removeAll();

        var estudio = this.getFiltroGrupos().down('#titulaciones').getValue();

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

        this.fixLoadMaskBug(store, this.getFiltroGrupos().down('#semestres'));
    },

    onSemestreSelected : function(combo, records)
    {
        this.getFiltroGrupos().down('#grupos').clearValue();

        var estudio = this.getFiltroGrupos().down('#titulaciones').getValue();
        var curso = this.getFiltroGrupos().down('#cursos').getValue();

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

        this.fixLoadMaskBug(store, this.getFiltroGrupos().down('#grupos'));
    },

    fixLoadMaskBug : function(store, combo)
    {
        store.on('load', function(store, records, successful, options)
        {
            if (successful && Ext.typeOf(combo.getPicker().loadMask) !== "boolean")
            {
                combo.getPicker().loadMask.hide();
            }
        });
    }
});