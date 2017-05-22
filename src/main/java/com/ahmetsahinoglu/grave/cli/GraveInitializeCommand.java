package com.ahmetsahinoglu.grave.cli;

import com.ahmetsahinoglu.grave.conf.GraveConfiguration;
import com.ahmetsahinoglu.grave.hibernate.entity.*;
import com.ahmetsahinoglu.grave.hibernate.enumtype.PriceType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Application;
import io.robe.admin.hibernate.entity.Menu;
import io.robe.admin.hibernate.entity.Role;
import io.robe.admin.hibernate.entity.User;
import io.robe.guice.GuiceBundle;
import io.robe.hibernate.RobeHibernateBundle;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GraveInitializeCommand extends io.robe.admin.cli.InitializeCommand<GraveConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraveInitializeCommand.class);

    @Inject
    protected RobeHibernateBundle hibernateBundle;

    public GraveInitializeCommand(Application<GraveConfiguration> service, String name, String description) {
        super(service, name, description);
        GuiceBundle.getInjector()
                .injectMembers(this);
        setHibernateBundle(hibernateBundle);
    }

    public static <T> List<T> readDataFromJson(Class<T> tClass, String fileName) {
        List<T> entityList = new ArrayList<>();
        InputStream inputStream = GraveInitializeCommand.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream != null) {
            try {
                LOGGER.info("reading file : " + fileName);
                ObjectMapper mapper = new ObjectMapper();
                entityList = mapper.readValue(inputStream, mapper.getTypeFactory().constructCollectionType(
                        List.class, tClass));
                LOGGER.info("Finish read file : " + fileName);
            } catch (IOException e) {
                LOGGER.error("Error reading file :" + fileName, e);
            }
        } else {
            LOGGER.error("Not found file :" + fileName + " error : " + inputStream);
        }
        return entityList;
    }

    public void setDataFromJson(Session session) {
        List<Province> provinceList = GraveInitializeCommand.<Province>readDataFromJson(Province.class, "provinces.json");
        List<District> districtList = GraveInitializeCommand.<District>readDataFromJson(District.class, "districts.json");

        for (Province province : provinceList) {
            session.persist(province);
            LOGGER.info("insert into Province " + province.getCode() + ", " + province.getName());
        }
        session.flush();

        for (District district : districtList) {
            Province province = (Province) session.createCriteria(Province.class)
                    .add(Restrictions.eq("code", district.getProvinceOid()))
                    .uniqueResult();
            district.setProvinceOid(province.getOid());
            session.persist(district);
            LOGGER.info("insert into District " + district.getCode() + ", " + district.getName() + ", " + district.getProvinceOid());
        }
        session.flush();
    }

    @Override
    public void execute(GraveConfiguration configuration) {
        super.execute(configuration);

        LOGGER.info("----- Opening session -----");
        final Session session = hibernateBundle.getSessionFactory()
                .openSession();

        Menu root = (Menu) session.createCriteria(Menu.class)
                .add(Restrictions.eq("path", "root"))
                .uniqueResult();
        Role role = (Role) session.createCriteria(Role.class)
                .add(Restrictions.eq("code", "io/robe/admin"))
                .uniqueResult();
        role.setName("Super Admin");

        User userAdmin = (User) session.createCriteria(User.class)
                .add(Restrictions.eq("email", EMAIL))
                .uniqueResult();
        userAdmin.setName("Super");
        userAdmin.setSurname("Admin");

        Role systemRole = new Role();
        systemRole.setCode("system");
        systemRole.setName("Sistem");
        session.persist(systemRole);

        Role graveAdminRole = new Role();
        graveAdminRole.setCode("admin");
        graveAdminRole.setName("Admin");
        session.persist(graveAdminRole);

        LOGGER.info("----- Creating menu -----");
        Menu grave = new Menu();
        grave.setPath("Grave");
        grave.setIndex(1);
        grave.setText("Site");
        grave.setParentOid(root.getOid());
        grave.setIcon("fa-cogs");
        grave.setModule("Grave");
        session.persist(grave);
        session.persist(createPermission(true, grave.getOid(), role));


        Menu burialAndDetail = new Menu();
        burialAndDetail.setPath("app/modules/site/burial/SelectAndBurial");
        burialAndDetail.setIndex(2);
        burialAndDetail.setText("Defin İşlemleri");
        burialAndDetail.setParentOid(grave.getOid());
        burialAndDetail.setIcon("fa fa-user-secret");
        burialAndDetail.setModule("SelectAndBurial");
        session.persist(burialAndDetail);
        session.persist(createPermission(true, burialAndDetail.getOid(), role));

        Menu buyGrave = new Menu();
        buyGrave.setPath("app/modules/site/buyGrave/BuyGrave");
        buyGrave.setIndex(3);
        buyGrave.setText("Mezar Satış");
        buyGrave.setParentOid(grave.getOid());
        buyGrave.setIcon("fa fa-user-secret");
        buyGrave.setModule("BuyGrave");
        session.persist(buyGrave);
        session.persist(createPermission(true, buyGrave.getOid(), role));

        Menu burialRecord = new Menu();
        burialRecord.setPath("app/modules/site/burialRecord/BurialRecord");
        burialRecord.setIndex(4);
        burialRecord.setText("Defin Kayıt");
        burialRecord.setParentOid(grave.getOid());
        burialRecord.setIcon("fa fa-user-secret");
        burialRecord.setModule("BurialRecord");
        session.persist(burialRecord);
        session.persist(createPermission(true, burialRecord.getOid(), role));

        Menu definition = new Menu();
        definition.setPath("app/modules/site/definition/Definition");
        definition.setIndex(5);
        definition.setText("Tanımlamalar");
        definition.setParentOid(grave.getOid());
        definition.setIcon("fa fa-user-secret");
        definition.setModule("Definition");
        session.persist(definition);
        session.persist(createPermission(true, definition.getOid(), role));

        Menu burialDetail = new Menu();
        burialDetail.setPath("app/modules/site/burialDetail/BurialDetail");
        burialDetail.setIndex(6);
        burialDetail.setText("Defin Detay");
        burialDetail.setParentOid(grave.getOid());
        burialDetail.setIcon("fa fa-user-secret");
        burialDetail.setModule("BurialDetail");
        session.persist(burialDetail);
        session.persist(createPermission(true, burialDetail.getOid(), role));

        setDataFromJson(session);


        initData(session);


        session.flush();
        session.close();

        LOGGER.info("----- Initialize finished -----");


    }

    private void initData(Session session) {

        Mosque mosque = new Mosque();
        mosque.setName("Merkez Camii");
        Mosque mosque1 = new Mosque();
        mosque1.setName("Florya Camii");
        Mosque mosque2 = new Mosque();
        mosque2.setName("Bakırköy Camii");
        Mosque mosque3 = new Mosque();
        mosque3.setName("Kocaeli Camii");
        session.persist(mosque);
        session.persist(mosque1);
        session.persist(mosque2);
        session.persist(mosque3);
        session.flush();

        DeadOfCause deadOfCause = new DeadOfCause();
        deadOfCause.setName("Beyin Kanaması");
        DeadOfCause deadOfCause1 = new DeadOfCause();
        deadOfCause1.setName("Akciğer Kanseri");
        DeadOfCause deadOfCause2 = new DeadOfCause();
        deadOfCause2.setName("Trafik Kazası");
        DeadOfCause deadOfCause3 = new DeadOfCause();
        deadOfCause3.setName("Kalp Krizi");
        session.persist(deadOfCause);
        session.persist(deadOfCause1);
        session.persist(deadOfCause2);
        session.persist(deadOfCause3);
        session.flush();

        GravePrice gravePrice = new GravePrice();
        gravePrice.setPriceType(PriceType.FREE);
        gravePrice.setUnitPrice(0);
        GravePrice gravePrice1 = new GravePrice();
        gravePrice1.setPriceType(PriceType.NEXT_TO_FREE);
        gravePrice1.setUnitPrice(100);
        GravePrice gravePrice2 = new GravePrice();
        gravePrice2.setPriceType(PriceType.NEXT_TO_PAID);
        gravePrice2.setUnitPrice(200);
        session.persist(gravePrice);
        session.persist(gravePrice1);
        session.persist(gravePrice2);
        session.flush();

        Personnel personnel = new Personnel();
        personnel.setName("Ahmet Mesut Şahinoğlu");
        personnel.setIdNumber("12667873640");
        personnel.setBirthDate(new Date());

        Personnel personnel1 = new Personnel();
        personnel1.setName("Murat Kaya");
        personnel1.setIdNumber("29980746640");
        personnel1.setBirthDate(new Date());

        Personnel personnel2 = new Personnel();
        personnel2.setName("Fatma Erdem");
        personnel2.setIdNumber("32546732240");
        personnel2.setBirthDate(new Date());

        Personnel personnel3 = new Personnel();
        personnel3.setName("Mehmet Kazmaz");
        personnel3.setIdNumber("49384736640");
        personnel3.setBirthDate(new Date());

        Personnel personnel4 = new Personnel();
        personnel4.setName("Meltem Ovalı");
        personnel4.setIdNumber("52665649340");
        personnel4.setBirthDate(new Date());

        Personnel personnel5 = new Personnel();
        personnel5.setName("Mustafa Yılmaz");
        personnel5.setIdNumber("62678903640");
        personnel5.setBirthDate(new Date());

        session.persist(personnel);
        session.persist(personnel1);
        session.persist(personnel2);
        session.persist(personnel3);
        session.persist(personnel4);
        session.persist(personnel5);
        session.flush();

    }
}
