// also supports: startTest(function(t) {
StartTest(function(t) {
    t.diag("Sanity");

    t.ok(Ext, 'ExtJS is here');
    t.ok(Ext.Window, '.. indeed');

    t.requireOk('HOR.model.SemestreDetalle');
    t.requireOk('HOR.controller.ControllerSemestreDetalle');
    t.requireOk('HOR.store.StoreSemestreDetalles');
    t.requireOk('HOR.view.semestres.PanelSemestres');   

});


