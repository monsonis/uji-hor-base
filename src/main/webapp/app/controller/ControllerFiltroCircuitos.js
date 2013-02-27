Ext.define('HOR.controller.ControllerFiltroCircuitos',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreEstudios', 'StoreSemestres', 'StoreGrupos' ],
    model : [ 'Estudio', 'Semestre', 'Grupo' ],
    refs : [
    {
        selector : 'filtroCircuitos',
        ref : 'filtroCircuitos'
    },
    {
        selector : 'selectorCircuitos',
        ref : 'selectorCircuitos'
    },
    {
        selector : 'panelCalendarioCircuitos',
        ref : 'panelCalendarioCircuitos'
    },
    {
        selector : 'panelCalendarioCircuitosDetalle',
        ref : 'panelCalendarioCircuitosDetalle'
    } ],
    init : function()
    {
        this.control(
        {
            'filtroCircuitos combobox[name=estudio]' :
            {
                select : this.onTitulacionSelected
            },

            'filtroCircuitos combobox[name=semestre]' :
            {
                select : this.onSemestreSelected
            },
            'filtroCircuitos combobox[name=grupo]' :
            {
                blur : function()
                {
                    if (this.getComboGrupos().getValue() == '')
                    {
                        this.limpiaPanelesCalendario();
                    }
                }
            }
        });
    },

    limpiaCamposComunes : function()
    {
        this.getFiltroCircuitos().down('combobox[name=grupo]').clearValue();
        this.limpiaPanelesCalendario();
    },

    limpiaPanelesCalendario : function()
    {
        if (this.getPanelCalendarioCircuitos())
        {
            this.getPanelCalendarioCircuitos().limpiaCalendario();
        }

        if (this.getPanelCalendarioCircuitosDetalle())
        {
            this.getPanelCalendarioCircuitosDetalle().limpiaCalendario();
        }

        this.getSelectorCircuitos().removeAll();
    },

    onTitulacionSelected : function(combo, records)
    {
        this.getFiltroCircuitos().down('combobox[name=semestre]').clearValue();
        this.limpiaCamposComunes();

        var store = this.getStoreSemestresStore();

        store.load(
        {
            params :
            {
                estudioId : records[0].get('id'),
                cursoId: 1
            },
            scope : this
        });
    },

    onSemestreSelected : function(combo, records)
    {
        this.limpiaCamposComunes();
        this.getStoreGruposStore().removeAll();

        var estudio = this.getFiltroCircuitos().down('combobox[name=estudio]').getValue();
        var store = this.getStoreGruposStore();

        store.load(
        {
            params :
            {
                estudioId : estudio,
                cursoId : 1,
                semestreId : records[0].data.semestre
            },
            scope : this
        });
    }

});