WHENEVER SQLERROR EXIT FAILURE ROLLBACK;

-----

INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.region.map', 'auc=Auckland,wlgn=Wellington,chch=Christchurch,lab=Lab',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));
       
INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.region.targetname.map', 'auc=dev1-TSPN_dev2-TSPN,wlgn=dev3-WGTN_dev4-WGTN,chch=dev5-TSTC_dev6-TSTC,lab=dev-LAB',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));
       
INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.order.realm.suffix.map', 'PRIMARY=.acc,SECONDARY=n.acc',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));
       
INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.sip.def.one.map', 'auc=218.101.10.7,wlgn=218.101.10.71,chch=218.101.10.135,lab=218.101.10.199',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));
       
INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.sip.def.two.map', 'auc=218.101.10.8,wlgn=218.101.10.72,chch=218.101.10.136,lab=218.101.10.200',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));

INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.circuit.type.map', 'W_SIP_LAYER_TWO=W-SIP Layer 2',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));
       
INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.access.network.interface.layer.two.map', 'auc=m01-access,wlgn=m01-access,chch=m01-access,lab=m00-access',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));
       
INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.network.interface.description', 'Connection to Access Network for customer %s',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));

INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.primary.access.realm.description', 'Access Realm for customer %s',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));

INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.secondary.access.realm.description', 'NAT Access Realm for customer %s',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));

INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.primary.sip.interface.description', 'Access SIP-Interface for customer %s',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));

INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.secondary.sip.interface.description', 'NAT Access SIP-Interface for customer %s',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));

INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.primary.access.realm.parent.realm', 'CS2K-pabx.acc',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));

INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.primary.access.realm.id', 'CS2K-%s-%s.acc',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));

INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.secondary.access.realm.id', 'CS2K-%s-%sn.acc',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));

INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.primary.access.realm.regex', 'CS2K-([a-zA-Z0-9]*)-(\d+).acc',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));

INSERT INTO app_property (id, app_name, key, value, eff_dt, term_dt)
VALUES (app_property_seq.nextval, 'admin-app',
       'config.secondary.access.realm.regex', 'CS2K-([a-zA-Z0-9]*)-(\d+)n.acc',
       trunc(sysdate), to_date('31-DEC-3999','DD-MON-YYYY'));


-----

SELECT count(*) FROM app_property WHERE app_name = 'admin-app';
       
COMMIT;