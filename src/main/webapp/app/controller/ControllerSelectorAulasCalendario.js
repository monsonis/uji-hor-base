Ext.define('HOR.controller.ControllerSelectorAulasCalendario',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreAulas', 'StoreCalendarios' ],
    refs : [
    {
        selector : 'selectorAulas',
        ref : 'selectorAulas'
    }  ,
    {
        selector : 'filtroAulas',
        ref : 'filtroAulas'
    },
    {
        selector : 'panelCalendarioAulas selectorCalendarios',
        ref : 'selectorCalendarios'
    } ],
    
    init : function()
    {
        this.control(
        {
            'filtroAulas combobox[name=edificio]' :
            {
                select : this.updateAulasFiltradas
            },
            'filtroAulas combobox[name=tipoAula]' :
            {
                select : this.updateAulasFiltradas
            },
            'filtroAulas combobox[name=planta]' :
            {
                select : this.updateAulasFiltradas
            }
        });
    },
    
    updateAulasFiltradas : function()
    {
        var store = this.getStoreAulasStore();
        
        var centro = this.getFiltroAulas().down('combobox[name=centro]').getValue();
        var semestre = this.getFiltroAulas().down('combobox[name=semestre]').getValue();
        var edificio = this.getFiltroAulas().down('combobox[name=edificio]').getValue();
        var tipoAula = this.getFiltroAulas().down('combobox[name=tipoAula]').getValue();
        var planta = this.getFiltroAulas().down('combobox[name=planta]').getValue();
        
        store.load(
        {
            callback : this.onAulasFiltradasLoaded,
            params :
            {
                centroId : centro,
                semestre : semestre,
                edificio : edificio,
                tipoAula : tipoAula,
                planta : planta
             },
             scope : this
        });

    },
    
    onAulasFiltradasLoaded : function(aulas, request)
    {
        var view = this.getSelectorAulas();

        view.removeAll();

        for ( var i = 0, len = aulas.length; i < len; i++)
        {
            var margin = '10 10 0 10';

            var button =
            {
                xtype : 'button',
                text : aulas[i].data.codigo,
                padding : '2 5 2 5',
                margin : margin,
                aulaId : aulas[i].data.id,
            };

            view.add(button);
        }
    }
});