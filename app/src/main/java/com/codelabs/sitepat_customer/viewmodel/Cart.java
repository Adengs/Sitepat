package com.codelabs.sitepat_customer.viewmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cart {

    @SerializedName("STATUS_CODE")
    private String statusCode;
    @SerializedName("MESSAGE")
    private String message;
    @SerializedName("DATA")
    private DATA data;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DATA getData() {
        return data;
    }

    public void setData(DATA data) {
        this.data = data;
    }

    public static class DATA {
        @SerializedName("cart_service_id")
        private int cartServiceId;
        @SerializedName("site_id")
        private int siteId;
        @SerializedName("customer_id")
        private int customerId;
        @SerializedName("customer_name")
        private String customerName;
        @SerializedName("is_invoice")
        private int isInvoice;
        @SerializedName("cart_service_type")
        private String cartServiceType;
        @SerializedName("order_code")
        private String orderCode;
        @SerializedName("pet_id")
        private int petId;
        @SerializedName("pet_name")
        private String petName;
        @SerializedName("plat_no")
        private String platNo;
        @SerializedName("car_color")
        private String carColor;
        @SerializedName("doctor_schedule_id")
        private int doctorScheduleId;
        @SerializedName("mechanic_schedule_id")
        private Object mechanicScheduleId;
        @SerializedName("grooming_location_schedule_id")
        private int groomingLocationScheduleId;
        @SerializedName("boarding_schedule_id")
        private int boardingScheduleId;
        @SerializedName("payment_complete")
        private int paymentComplete;
        @SerializedName("has_anamnesis")
        private int hasAnamnesis;
        @SerializedName("take_action")
        private int takeAction;
        @SerializedName("order_date")
        private String orderDate;
        @SerializedName("cart_service_status")
        private int cartServiceStatus;
        @SerializedName("complaint")
        private String complaint;
        @SerializedName("cart_canceled_at")
        private Object cartCanceledAt;
        @SerializedName("done_at")
        private Object doneAt;
        @SerializedName("work_status")
        private int workStatus;
        @SerializedName("general_checkup_start")
        private Object generalCheckupStart;
        @SerializedName("general_checkup_finish")
        private Object generalCheckupFinish;
        @SerializedName("take_action_start")
        private Object takeActionStart;
        @SerializedName("take_action_finish")
        private Object takeActionFinish;
        @SerializedName("work_checking_start")
        private Object workCheckingStart;
        @SerializedName("work_checking_finish")
        private Object workCheckingFinish;
        @SerializedName("latitude")
        private Object latitude;
        @SerializedName("longitude")
        private Object longitude;
        @SerializedName("address")
        private Object address;
        @SerializedName("note")
        private Object note;
        @SerializedName("created_by")
        private int createdBy;
        @SerializedName("updated_by")
        private int updatedBy;
        @SerializedName("deleted_at")
        private Object deletedAt;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("service_type")
        private int serviceType;
        @SerializedName("doctor_schedule")
        private DoctorSchedule doctorSchedule;
        @SerializedName("grooming_location_schedule")
        private GroomingLocationSchedule groomingLocationSchedule;
        @SerializedName("boarding_schedule")
        private BoardingSchedule boardingSchedule;
        @SerializedName("pet")
        private Pet pet;
        @SerializedName("customer")
        private Customer customer;
        @SerializedName("cart_service_status_detail")
        private String cartServiceStatusDetail;
        @SerializedName("service_medical")
        private List<?> serviceMedical;
        @SerializedName("general_checkup")
        private List<?> generalCheckup;
        @SerializedName("work_order")
        private List<?> workOrder;
        @SerializedName("work_checking")
        private List<?> workChecking;
        @SerializedName("service_grooming")
        private List<?> serviceGrooming;
        @SerializedName("service_boarding")
        private List<?> serviceBoarding;
        @SerializedName("medical_items")
        private List<?> medicalItems;

        public int getCartServiceId() {
            return cartServiceId;
        }

        public void setCartServiceId(int cartServiceId) {
            this.cartServiceId = cartServiceId;
        }

        public int getSiteId() {
            return siteId;
        }

        public void setSiteId(int siteId) {
            this.siteId = siteId;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public int getIsInvoice() {
            return isInvoice;
        }

        public void setIsInvoice(int isInvoice) {
            this.isInvoice = isInvoice;
        }

        public String getCartServiceType() {
            return cartServiceType;
        }

        public void setCartServiceType(String cartServiceType) {
            this.cartServiceType = cartServiceType;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public int getPetId() {
            return petId;
        }

        public void setPetId(int petId) {
            this.petId = petId;
        }

        public String getPetName() {
            return petName;
        }

        public void setPetName(String petName) {
            this.petName = petName;
        }

        public String getPlatNo() {
            return platNo;
        }

        public void setPlatNo(String platNo) {
            this.platNo = platNo;
        }

        public String getCarColor() {
            return carColor;
        }

        public void setCarColor(String carColor) {
            this.carColor = carColor;
        }

        public int getDoctorScheduleId() {
            return doctorScheduleId;
        }

        public void setDoctorScheduleId(int doctorScheduleId) {
            this.doctorScheduleId = doctorScheduleId;
        }

        public Object getMechanicScheduleId() {
            return mechanicScheduleId;
        }

        public void setMechanicScheduleId(Object mechanicScheduleId) {
            this.mechanicScheduleId = mechanicScheduleId;
        }

        public int getGroomingLocationScheduleId() {
            return groomingLocationScheduleId;
        }

        public void setGroomingLocationScheduleId(int groomingLocationScheduleId) {
            this.groomingLocationScheduleId = groomingLocationScheduleId;
        }

        public int getBoardingScheduleId() {
            return boardingScheduleId;
        }

        public void setBoardingScheduleId(int boardingScheduleId) {
            this.boardingScheduleId = boardingScheduleId;
        }

        public int getPaymentComplete() {
            return paymentComplete;
        }

        public void setPaymentComplete(int paymentComplete) {
            this.paymentComplete = paymentComplete;
        }

        public int getHasAnamnesis() {
            return hasAnamnesis;
        }

        public void setHasAnamnesis(int hasAnamnesis) {
            this.hasAnamnesis = hasAnamnesis;
        }

        public int getTakeAction() {
            return takeAction;
        }

        public void setTakeAction(int takeAction) {
            this.takeAction = takeAction;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public int getCartServiceStatus() {
            return cartServiceStatus;
        }

        public void setCartServiceStatus(int cartServiceStatus) {
            this.cartServiceStatus = cartServiceStatus;
        }

        public String getComplaint() {
            return complaint;
        }

        public void setComplaint(String complaint) {
            this.complaint = complaint;
        }

        public Object getCartCanceledAt() {
            return cartCanceledAt;
        }

        public void setCartCanceledAt(Object cartCanceledAt) {
            this.cartCanceledAt = cartCanceledAt;
        }

        public Object getDoneAt() {
            return doneAt;
        }

        public void setDoneAt(Object doneAt) {
            this.doneAt = doneAt;
        }

        public int getWorkStatus() {
            return workStatus;
        }

        public void setWorkStatus(int workStatus) {
            this.workStatus = workStatus;
        }

        public Object getGeneralCheckupStart() {
            return generalCheckupStart;
        }

        public void setGeneralCheckupStart(Object generalCheckupStart) {
            this.generalCheckupStart = generalCheckupStart;
        }

        public Object getGeneralCheckupFinish() {
            return generalCheckupFinish;
        }

        public void setGeneralCheckupFinish(Object generalCheckupFinish) {
            this.generalCheckupFinish = generalCheckupFinish;
        }

        public Object getTakeActionStart() {
            return takeActionStart;
        }

        public void setTakeActionStart(Object takeActionStart) {
            this.takeActionStart = takeActionStart;
        }

        public Object getTakeActionFinish() {
            return takeActionFinish;
        }

        public void setTakeActionFinish(Object takeActionFinish) {
            this.takeActionFinish = takeActionFinish;
        }

        public Object getWorkCheckingStart() {
            return workCheckingStart;
        }

        public void setWorkCheckingStart(Object workCheckingStart) {
            this.workCheckingStart = workCheckingStart;
        }

        public Object getWorkCheckingFinish() {
            return workCheckingFinish;
        }

        public void setWorkCheckingFinish(Object workCheckingFinish) {
            this.workCheckingFinish = workCheckingFinish;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getNote() {
            return note;
        }

        public void setNote(Object note) {
            this.note = note;
        }

        public int getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(int createdBy) {
            this.createdBy = createdBy;
        }

        public int getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(int updatedBy) {
            this.updatedBy = updatedBy;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getServiceType() {
            return serviceType;
        }

        public void setServiceType(int serviceType) {
            this.serviceType = serviceType;
        }

        public DoctorSchedule getDoctorSchedule() {
            return doctorSchedule;
        }

        public void setDoctorSchedule(DoctorSchedule doctorSchedule) {
            this.doctorSchedule = doctorSchedule;
        }

        public GroomingLocationSchedule getGroomingLocationSchedule() {
            return groomingLocationSchedule;
        }

        public void setGroomingLocationSchedule(GroomingLocationSchedule groomingLocationSchedule) {
            this.groomingLocationSchedule = groomingLocationSchedule;
        }

        public BoardingSchedule getBoardingSchedule() {
            return boardingSchedule;
        }

        public void setBoardingSchedule(BoardingSchedule boardingSchedule) {
            this.boardingSchedule = boardingSchedule;
        }

        public Pet getPet() {
            return pet;
        }

        public void setPet(Pet pet) {
            this.pet = pet;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public String getCartServiceStatusDetail() {
            return cartServiceStatusDetail;
        }

        public void setCartServiceStatusDetail(String cartServiceStatusDetail) {
            this.cartServiceStatusDetail = cartServiceStatusDetail;
        }

        public List<?> getServiceMedical() {
            return serviceMedical;
        }

        public void setServiceMedical(List<?> serviceMedical) {
            this.serviceMedical = serviceMedical;
        }

        public List<?> getGeneralCheckup() {
            return generalCheckup;
        }

        public void setGeneralCheckup(List<?> generalCheckup) {
            this.generalCheckup = generalCheckup;
        }

        public List<?> getWorkOrder() {
            return workOrder;
        }

        public void setWorkOrder(List<?> workOrder) {
            this.workOrder = workOrder;
        }

        public List<?> getWorkChecking() {
            return workChecking;
        }

        public void setWorkChecking(List<?> workChecking) {
            this.workChecking = workChecking;
        }

        public List<?> getServiceGrooming() {
            return serviceGrooming;
        }

        public void setServiceGrooming(List<?> serviceGrooming) {
            this.serviceGrooming = serviceGrooming;
        }

        public List<?> getServiceBoarding() {
            return serviceBoarding;
        }

        public void setServiceBoarding(List<?> serviceBoarding) {
            this.serviceBoarding = serviceBoarding;
        }

        public List<?> getMedicalItems() {
            return medicalItems;
        }

        public void setMedicalItems(List<?> medicalItems) {
            this.medicalItems = medicalItems;
        }

        public static class DoctorSchedule {
            @SerializedName("name")
            private String name;
            @SerializedName("doctor_schedule_id")
            private int doctorScheduleId;
            @SerializedName("staff_id")
            private int staffId;
            @SerializedName("reservation_id")
            private int reservationId;
            @SerializedName("pet_id")
            private int petId;
            @SerializedName("schedule_date")
            private String scheduleDate;
            @SerializedName("schedule_time")
            private String scheduleTime;
            @SerializedName("schedule_times")
            private List<?> scheduleTimes;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getDoctorScheduleId() {
                return doctorScheduleId;
            }

            public void setDoctorScheduleId(int doctorScheduleId) {
                this.doctorScheduleId = doctorScheduleId;
            }

            public int getStaffId() {
                return staffId;
            }

            public void setStaffId(int staffId) {
                this.staffId = staffId;
            }

            public int getReservationId() {
                return reservationId;
            }

            public void setReservationId(int reservationId) {
                this.reservationId = reservationId;
            }

            public int getPetId() {
                return petId;
            }

            public void setPetId(int petId) {
                this.petId = petId;
            }

            public String getScheduleDate() {
                return scheduleDate;
            }

            public void setScheduleDate(String scheduleDate) {
                this.scheduleDate = scheduleDate;
            }

            public String getScheduleTime() {
                return scheduleTime;
            }

            public void setScheduleTime(String scheduleTime) {
                this.scheduleTime = scheduleTime;
            }

            public List<?> getScheduleTimes() {
                return scheduleTimes;
            }

            public void setScheduleTimes(List<?> scheduleTimes) {
                this.scheduleTimes = scheduleTimes;
            }
        }

        public static class GroomingLocationSchedule {
            @SerializedName("location_name")
            private String locationName;
            @SerializedName("grooming_location_schedule_id")
            private int groomingLocationScheduleId;
            @SerializedName("grooming_location_id")
            private int groomingLocationId;
            @SerializedName("reservation_id")
            private int reservationId;
            @SerializedName("customer_id")
            private int customerId;
            @SerializedName("pet_id")
            private int petId;
            @SerializedName("schedule_date")
            private String scheduleDate;
            @SerializedName("schedule_time")
            private String scheduleTime;

            public String getLocationName() {
                return locationName;
            }

            public void setLocationName(String locationName) {
                this.locationName = locationName;
            }

            public int getGroomingLocationScheduleId() {
                return groomingLocationScheduleId;
            }

            public void setGroomingLocationScheduleId(int groomingLocationScheduleId) {
                this.groomingLocationScheduleId = groomingLocationScheduleId;
            }

            public int getGroomingLocationId() {
                return groomingLocationId;
            }

            public void setGroomingLocationId(int groomingLocationId) {
                this.groomingLocationId = groomingLocationId;
            }

            public int getReservationId() {
                return reservationId;
            }

            public void setReservationId(int reservationId) {
                this.reservationId = reservationId;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public int getPetId() {
                return petId;
            }

            public void setPetId(int petId) {
                this.petId = petId;
            }

            public String getScheduleDate() {
                return scheduleDate;
            }

            public void setScheduleDate(String scheduleDate) {
                this.scheduleDate = scheduleDate;
            }

            public String getScheduleTime() {
                return scheduleTime;
            }

            public void setScheduleTime(String scheduleTime) {
                this.scheduleTime = scheduleTime;
            }
        }

        public static class BoardingSchedule {
            @SerializedName("cage_name")
            private String cageName;
            @SerializedName("cage_price")
            private int cagePrice;
            @SerializedName("cage_schedule_id")
            private int cageScheduleId;
            @SerializedName("cage_id")
            private int cageId;
            @SerializedName("reservation_id")
            private int reservationId;
            @SerializedName("customer_id")
            private int customerId;
            @SerializedName("pet_id")
            private int petId;
            @SerializedName("schedule_date_from")
            private String scheduleDateFrom;
            @SerializedName("schedule_date_until")
            private String scheduleDateUntil;

            public String getCageName() {
                return cageName;
            }

            public void setCageName(String cageName) {
                this.cageName = cageName;
            }

            public int getCagePrice() {
                return cagePrice;
            }

            public void setCagePrice(int cagePrice) {
                this.cagePrice = cagePrice;
            }

            public int getCageScheduleId() {
                return cageScheduleId;
            }

            public void setCageScheduleId(int cageScheduleId) {
                this.cageScheduleId = cageScheduleId;
            }

            public int getCageId() {
                return cageId;
            }

            public void setCageId(int cageId) {
                this.cageId = cageId;
            }

            public int getReservationId() {
                return reservationId;
            }

            public void setReservationId(int reservationId) {
                this.reservationId = reservationId;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public int getPetId() {
                return petId;
            }

            public void setPetId(int petId) {
                this.petId = petId;
            }

            public String getScheduleDateFrom() {
                return scheduleDateFrom;
            }

            public void setScheduleDateFrom(String scheduleDateFrom) {
                this.scheduleDateFrom = scheduleDateFrom;
            }

            public String getScheduleDateUntil() {
                return scheduleDateUntil;
            }

            public void setScheduleDateUntil(String scheduleDateUntil) {
                this.scheduleDateUntil = scheduleDateUntil;
            }
        }

        public static class Pet {
            @SerializedName("customer_name")
            private String customerName;
            @SerializedName("pet_id")
            private int petId;
            @SerializedName("site_id")
            private String siteId;
            @SerializedName("pet_color")
            private String petColor;
            @SerializedName("pet_microchip")
            private String petMicrochip;
            @SerializedName("pet_breed")
            private String petBreed;
            @SerializedName("customer_id")
            private int customerId;
            @SerializedName("car_brand_id")
            private int carBrandId;
            @SerializedName("car_brand_type_id")
            private int carBrandTypeId;
            @SerializedName("pet_name")
            private String petName;
            @SerializedName("pet_race")
            private String petRace;
            @SerializedName("pet_birthday")
            private String petBirthday;
            @SerializedName("pet_gender")
            private String petGender;
            @SerializedName("pet_photo")
            private String petPhoto;
            @SerializedName("created_on")
            private String createdOn;
            @SerializedName("updated_on")
            private String updatedOn;
            @SerializedName("pet_special_notes")
            private Object petSpecialNotes;
            @SerializedName("is_maintenance")
            private int isMaintenance;
            @SerializedName("created_by")
            private int createdBy;
            @SerializedName("updated_by")
            private int updatedBy;
            @SerializedName("deleted_at")
            private Object deletedAt;
            @SerializedName("created_at")
            private String createdAt;
            @SerializedName("updated_at")
            private String updatedAt;
            @SerializedName("pet_code")
            private String petCode;
            @SerializedName("customer_code")
            private String customerCode;
            @SerializedName("brand")
            private Brand brand;
            @SerializedName("brand_type")
            private BrandType brandType;

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public int getPetId() {
                return petId;
            }

            public void setPetId(int petId) {
                this.petId = petId;
            }

            public String getSiteId() {
                return siteId;
            }

            public void setSiteId(String siteId) {
                this.siteId = siteId;
            }

            public String getPetColor() {
                return petColor;
            }

            public void setPetColor(String petColor) {
                this.petColor = petColor;
            }

            public String getPetMicrochip() {
                return petMicrochip;
            }

            public void setPetMicrochip(String petMicrochip) {
                this.petMicrochip = petMicrochip;
            }

            public String getPetBreed() {
                return petBreed;
            }

            public void setPetBreed(String petBreed) {
                this.petBreed = petBreed;
            }

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public int getCarBrandId() {
                return carBrandId;
            }

            public void setCarBrandId(int carBrandId) {
                this.carBrandId = carBrandId;
            }

            public int getCarBrandTypeId() {
                return carBrandTypeId;
            }

            public void setCarBrandTypeId(int carBrandTypeId) {
                this.carBrandTypeId = carBrandTypeId;
            }

            public String getPetName() {
                return petName;
            }

            public void setPetName(String petName) {
                this.petName = petName;
            }

            public String getPetRace() {
                return petRace;
            }

            public void setPetRace(String petRace) {
                this.petRace = petRace;
            }

            public String getPetBirthday() {
                return petBirthday;
            }

            public void setPetBirthday(String petBirthday) {
                this.petBirthday = petBirthday;
            }

            public String getPetGender() {
                return petGender;
            }

            public void setPetGender(String petGender) {
                this.petGender = petGender;
            }

            public String getPetPhoto() {
                return petPhoto;
            }

            public void setPetPhoto(String petPhoto) {
                this.petPhoto = petPhoto;
            }

            public String getCreatedOn() {
                return createdOn;
            }

            public void setCreatedOn(String createdOn) {
                this.createdOn = createdOn;
            }

            public String getUpdatedOn() {
                return updatedOn;
            }

            public void setUpdatedOn(String updatedOn) {
                this.updatedOn = updatedOn;
            }

            public Object getPetSpecialNotes() {
                return petSpecialNotes;
            }

            public void setPetSpecialNotes(Object petSpecialNotes) {
                this.petSpecialNotes = petSpecialNotes;
            }

            public int getIsMaintenance() {
                return isMaintenance;
            }

            public void setIsMaintenance(int isMaintenance) {
                this.isMaintenance = isMaintenance;
            }

            public int getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(int createdBy) {
                this.createdBy = createdBy;
            }

            public int getUpdatedBy() {
                return updatedBy;
            }

            public void setUpdatedBy(int updatedBy) {
                this.updatedBy = updatedBy;
            }

            public Object getDeletedAt() {
                return deletedAt;
            }

            public void setDeletedAt(Object deletedAt) {
                this.deletedAt = deletedAt;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public String getPetCode() {
                return petCode;
            }

            public void setPetCode(String petCode) {
                this.petCode = petCode;
            }

            public String getCustomerCode() {
                return customerCode;
            }

            public void setCustomerCode(String customerCode) {
                this.customerCode = customerCode;
            }

            public Brand getBrand() {
                return brand;
            }

            public void setBrand(Brand brand) {
                this.brand = brand;
            }

            public BrandType getBrandType() {
                return brandType;
            }

            public void setBrandType(BrandType brandType) {
                this.brandType = brandType;
            }

            public static class Brand {
                @SerializedName("brand_id")
                private int brandId;
                @SerializedName("brand_code")
                private String brandCode;
                @SerializedName("brand_name")
                private String brandName;
                @SerializedName("brand_description")
                private String brandDescription;
                @SerializedName("is_brand_active")
                private int isBrandActive;
                @SerializedName("created_on")
                private String createdOn;
                @SerializedName("updated_on")
                private String updatedOn;
                @SerializedName("created_by")
                private int createdBy;
                @SerializedName("updated_by")
                private int updatedBy;
                @SerializedName("created_at")
                private String createdAt;
                @SerializedName("updated_at")
                private String updatedAt;
                @SerializedName("deleted_at")
                private Object deletedAt;

                public int getBrandId() {
                    return brandId;
                }

                public void setBrandId(int brandId) {
                    this.brandId = brandId;
                }

                public String getBrandCode() {
                    return brandCode;
                }

                public void setBrandCode(String brandCode) {
                    this.brandCode = brandCode;
                }

                public String getBrandName() {
                    return brandName;
                }

                public void setBrandName(String brandName) {
                    this.brandName = brandName;
                }

                public String getBrandDescription() {
                    return brandDescription;
                }

                public void setBrandDescription(String brandDescription) {
                    this.brandDescription = brandDescription;
                }

                public int getIsBrandActive() {
                    return isBrandActive;
                }

                public void setIsBrandActive(int isBrandActive) {
                    this.isBrandActive = isBrandActive;
                }

                public String getCreatedOn() {
                    return createdOn;
                }

                public void setCreatedOn(String createdOn) {
                    this.createdOn = createdOn;
                }

                public String getUpdatedOn() {
                    return updatedOn;
                }

                public void setUpdatedOn(String updatedOn) {
                    this.updatedOn = updatedOn;
                }

                public int getCreatedBy() {
                    return createdBy;
                }

                public void setCreatedBy(int createdBy) {
                    this.createdBy = createdBy;
                }

                public int getUpdatedBy() {
                    return updatedBy;
                }

                public void setUpdatedBy(int updatedBy) {
                    this.updatedBy = updatedBy;
                }

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }

                public String getUpdatedAt() {
                    return updatedAt;
                }

                public void setUpdatedAt(String updatedAt) {
                    this.updatedAt = updatedAt;
                }

                public Object getDeletedAt() {
                    return deletedAt;
                }

                public void setDeletedAt(Object deletedAt) {
                    this.deletedAt = deletedAt;
                }
            }

            public static class BrandType {
                @SerializedName("type_id")
                private int typeId;
                @SerializedName("type_brand_id")
                private int typeBrandId;
                @SerializedName("type_code")
                private String typeCode;
                @SerializedName("type_name")
                private String typeName;
                @SerializedName("type_description")
                private String typeDescription;
                @SerializedName("is_type_active")
                private int isTypeActive;
                @SerializedName("created_on")
                private String createdOn;
                @SerializedName("updated_on")
                private String updatedOn;
                @SerializedName("created_by")
                private int createdBy;
                @SerializedName("updated_by")
                private int updatedBy;
                @SerializedName("created_at")
                private String createdAt;
                @SerializedName("updated_at")
                private String updatedAt;
                @SerializedName("deleted_at")
                private Object deletedAt;

                public int getTypeId() {
                    return typeId;
                }

                public void setTypeId(int typeId) {
                    this.typeId = typeId;
                }

                public int getTypeBrandId() {
                    return typeBrandId;
                }

                public void setTypeBrandId(int typeBrandId) {
                    this.typeBrandId = typeBrandId;
                }

                public String getTypeCode() {
                    return typeCode;
                }

                public void setTypeCode(String typeCode) {
                    this.typeCode = typeCode;
                }

                public String getTypeName() {
                    return typeName;
                }

                public void setTypeName(String typeName) {
                    this.typeName = typeName;
                }

                public String getTypeDescription() {
                    return typeDescription;
                }

                public void setTypeDescription(String typeDescription) {
                    this.typeDescription = typeDescription;
                }

                public int getIsTypeActive() {
                    return isTypeActive;
                }

                public void setIsTypeActive(int isTypeActive) {
                    this.isTypeActive = isTypeActive;
                }

                public String getCreatedOn() {
                    return createdOn;
                }

                public void setCreatedOn(String createdOn) {
                    this.createdOn = createdOn;
                }

                public String getUpdatedOn() {
                    return updatedOn;
                }

                public void setUpdatedOn(String updatedOn) {
                    this.updatedOn = updatedOn;
                }

                public int getCreatedBy() {
                    return createdBy;
                }

                public void setCreatedBy(int createdBy) {
                    this.createdBy = createdBy;
                }

                public int getUpdatedBy() {
                    return updatedBy;
                }

                public void setUpdatedBy(int updatedBy) {
                    this.updatedBy = updatedBy;
                }

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }

                public String getUpdatedAt() {
                    return updatedAt;
                }

                public void setUpdatedAt(String updatedAt) {
                    this.updatedAt = updatedAt;
                }

                public Object getDeletedAt() {
                    return deletedAt;
                }

                public void setDeletedAt(Object deletedAt) {
                    this.deletedAt = deletedAt;
                }
            }
        }

        public static class Customer {
            @SerializedName("customer_id")
            private int customerId;
            @SerializedName("site_id")
            private int siteId;
            @SerializedName("customer_name")
            private String customerName;
            @SerializedName("customer_email")
            private String customerEmail;
            @SerializedName("customer_gender")
            private String customerGender;
            @SerializedName("customer_phone")
            private String customerPhone;
            @SerializedName("customer_address")
            private String customerAddress;
            @SerializedName("customer_photo")
            private String customerPhoto;
            @SerializedName("facebook_id")
            private String facebookId;
            @SerializedName("google_id")
            private Object googleId;
            @SerializedName("total_point")
            private int totalPoint;
            @SerializedName("fp_code")
            private String fpCode;
            @SerializedName("fp_datetime")
            private String fpDatetime;
            @SerializedName("fp_attempt")
            private int fpAttempt;
            @SerializedName("is_activated")
            private int isActivated;
            @SerializedName("customer_group")
            private int customerGroup;
            @SerializedName("created_on")
            private String createdOn;
            @SerializedName("updated_on")
            private String updatedOn;
            @SerializedName("created_by")
            private int createdBy;
            @SerializedName("updated_by")
            private int updatedBy;
            @SerializedName("deleted_at")
            private Object deletedAt;
            @SerializedName("created_at")
            private String createdAt;
            @SerializedName("updated_at")
            private String updatedAt;
            @SerializedName("customer_code")
            private String customerCode;
            @SerializedName("pets")
            private List<Pets> pets;

            public int getCustomerId() {
                return customerId;
            }

            public void setCustomerId(int customerId) {
                this.customerId = customerId;
            }

            public int getSiteId() {
                return siteId;
            }

            public void setSiteId(int siteId) {
                this.siteId = siteId;
            }

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getCustomerEmail() {
                return customerEmail;
            }

            public void setCustomerEmail(String customerEmail) {
                this.customerEmail = customerEmail;
            }

            public String getCustomerGender() {
                return customerGender;
            }

            public void setCustomerGender(String customerGender) {
                this.customerGender = customerGender;
            }

            public String getCustomerPhone() {
                return customerPhone;
            }

            public void setCustomerPhone(String customerPhone) {
                this.customerPhone = customerPhone;
            }

            public String getCustomerAddress() {
                return customerAddress;
            }

            public void setCustomerAddress(String customerAddress) {
                this.customerAddress = customerAddress;
            }

            public String getCustomerPhoto() {
                return customerPhoto;
            }

            public void setCustomerPhoto(String customerPhoto) {
                this.customerPhoto = customerPhoto;
            }

            public String getFacebookId() {
                return facebookId;
            }

            public void setFacebookId(String facebookId) {
                this.facebookId = facebookId;
            }

            public Object getGoogleId() {
                return googleId;
            }

            public void setGoogleId(Object googleId) {
                this.googleId = googleId;
            }

            public int getTotalPoint() {
                return totalPoint;
            }

            public void setTotalPoint(int totalPoint) {
                this.totalPoint = totalPoint;
            }

            public String getFpCode() {
                return fpCode;
            }

            public void setFpCode(String fpCode) {
                this.fpCode = fpCode;
            }

            public String getFpDatetime() {
                return fpDatetime;
            }

            public void setFpDatetime(String fpDatetime) {
                this.fpDatetime = fpDatetime;
            }

            public int getFpAttempt() {
                return fpAttempt;
            }

            public void setFpAttempt(int fpAttempt) {
                this.fpAttempt = fpAttempt;
            }

            public int getIsActivated() {
                return isActivated;
            }

            public void setIsActivated(int isActivated) {
                this.isActivated = isActivated;
            }

            public int getCustomerGroup() {
                return customerGroup;
            }

            public void setCustomerGroup(int customerGroup) {
                this.customerGroup = customerGroup;
            }

            public String getCreatedOn() {
                return createdOn;
            }

            public void setCreatedOn(String createdOn) {
                this.createdOn = createdOn;
            }

            public String getUpdatedOn() {
                return updatedOn;
            }

            public void setUpdatedOn(String updatedOn) {
                this.updatedOn = updatedOn;
            }

            public int getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(int createdBy) {
                this.createdBy = createdBy;
            }

            public int getUpdatedBy() {
                return updatedBy;
            }

            public void setUpdatedBy(int updatedBy) {
                this.updatedBy = updatedBy;
            }

            public Object getDeletedAt() {
                return deletedAt;
            }

            public void setDeletedAt(Object deletedAt) {
                this.deletedAt = deletedAt;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public String getCustomerCode() {
                return customerCode;
            }

            public void setCustomerCode(String customerCode) {
                this.customerCode = customerCode;
            }

            public List<Pets> getPets() {
                return pets;
            }

            public void setPets(List<Pets> pets) {
                this.pets = pets;
            }

            public static class Pets {
                @SerializedName("pet_id")
                private int petId;
                @SerializedName("site_id")
                private String siteId;
                @SerializedName("pet_color")
                private String petColor;
                @SerializedName("pet_microchip")
                private String petMicrochip;
                @SerializedName("pet_breed")
                private String petBreed;
                @SerializedName("customer_id")
                private int customerId;
                @SerializedName("car_brand_id")
                private int carBrandId;
                @SerializedName("car_brand_type_id")
                private int carBrandTypeId;
                @SerializedName("pet_name")
                private String petName;
                @SerializedName("pet_race")
                private String petRace;
                @SerializedName("pet_birthday")
                private String petBirthday;
                @SerializedName("pet_gender")
                private String petGender;
                @SerializedName("pet_photo")
                private String petPhoto;
                @SerializedName("created_on")
                private String createdOn;
                @SerializedName("updated_on")
                private String updatedOn;
                @SerializedName("pet_special_notes")
                private Object petSpecialNotes;
                @SerializedName("is_maintenance")
                private int isMaintenance;
                @SerializedName("created_by")
                private int createdBy;
                @SerializedName("updated_by")
                private int updatedBy;
                @SerializedName("deleted_at")
                private Object deletedAt;
                @SerializedName("created_at")
                private String createdAt;
                @SerializedName("updated_at")
                private String updatedAt;
                @SerializedName("pet_code")
                private String petCode;
                @SerializedName("customer_code")
                private String customerCode;
                @SerializedName("brand")
                private Brand brand;
                @SerializedName("brand_type")
                private BrandType brandType;

                public int getPetId() {
                    return petId;
                }

                public void setPetId(int petId) {
                    this.petId = petId;
                }

                public String getSiteId() {
                    return siteId;
                }

                public void setSiteId(String siteId) {
                    this.siteId = siteId;
                }

                public String getPetColor() {
                    return petColor;
                }

                public void setPetColor(String petColor) {
                    this.petColor = petColor;
                }

                public String getPetMicrochip() {
                    return petMicrochip;
                }

                public void setPetMicrochip(String petMicrochip) {
                    this.petMicrochip = petMicrochip;
                }

                public String getPetBreed() {
                    return petBreed;
                }

                public void setPetBreed(String petBreed) {
                    this.petBreed = petBreed;
                }

                public int getCustomerId() {
                    return customerId;
                }

                public void setCustomerId(int customerId) {
                    this.customerId = customerId;
                }

                public int getCarBrandId() {
                    return carBrandId;
                }

                public void setCarBrandId(int carBrandId) {
                    this.carBrandId = carBrandId;
                }

                public int getCarBrandTypeId() {
                    return carBrandTypeId;
                }

                public void setCarBrandTypeId(int carBrandTypeId) {
                    this.carBrandTypeId = carBrandTypeId;
                }

                public String getPetName() {
                    return petName;
                }

                public void setPetName(String petName) {
                    this.petName = petName;
                }

                public String getPetRace() {
                    return petRace;
                }

                public void setPetRace(String petRace) {
                    this.petRace = petRace;
                }

                public String getPetBirthday() {
                    return petBirthday;
                }

                public void setPetBirthday(String petBirthday) {
                    this.petBirthday = petBirthday;
                }

                public String getPetGender() {
                    return petGender;
                }

                public void setPetGender(String petGender) {
                    this.petGender = petGender;
                }

                public String getPetPhoto() {
                    return petPhoto;
                }

                public void setPetPhoto(String petPhoto) {
                    this.petPhoto = petPhoto;
                }

                public String getCreatedOn() {
                    return createdOn;
                }

                public void setCreatedOn(String createdOn) {
                    this.createdOn = createdOn;
                }

                public String getUpdatedOn() {
                    return updatedOn;
                }

                public void setUpdatedOn(String updatedOn) {
                    this.updatedOn = updatedOn;
                }

                public Object getPetSpecialNotes() {
                    return petSpecialNotes;
                }

                public void setPetSpecialNotes(Object petSpecialNotes) {
                    this.petSpecialNotes = petSpecialNotes;
                }

                public int getIsMaintenance() {
                    return isMaintenance;
                }

                public void setIsMaintenance(int isMaintenance) {
                    this.isMaintenance = isMaintenance;
                }

                public int getCreatedBy() {
                    return createdBy;
                }

                public void setCreatedBy(int createdBy) {
                    this.createdBy = createdBy;
                }

                public int getUpdatedBy() {
                    return updatedBy;
                }

                public void setUpdatedBy(int updatedBy) {
                    this.updatedBy = updatedBy;
                }

                public Object getDeletedAt() {
                    return deletedAt;
                }

                public void setDeletedAt(Object deletedAt) {
                    this.deletedAt = deletedAt;
                }

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }

                public String getUpdatedAt() {
                    return updatedAt;
                }

                public void setUpdatedAt(String updatedAt) {
                    this.updatedAt = updatedAt;
                }

                public String getPetCode() {
                    return petCode;
                }

                public void setPetCode(String petCode) {
                    this.petCode = petCode;
                }

                public String getCustomerCode() {
                    return customerCode;
                }

                public void setCustomerCode(String customerCode) {
                    this.customerCode = customerCode;
                }

                public Brand getBrand() {
                    return brand;
                }

                public void setBrand(Brand brand) {
                    this.brand = brand;
                }

                public BrandType getBrandType() {
                    return brandType;
                }

                public void setBrandType(BrandType brandType) {
                    this.brandType = brandType;
                }

                public static class Brand {
                    @SerializedName("brand_id")
                    private int brandId;
                    @SerializedName("brand_code")
                    private String brandCode;
                    @SerializedName("brand_name")
                    private String brandName;
                    @SerializedName("brand_description")
                    private String brandDescription;
                    @SerializedName("is_brand_active")
                    private int isBrandActive;
                    @SerializedName("created_on")
                    private String createdOn;
                    @SerializedName("updated_on")
                    private String updatedOn;
                    @SerializedName("created_by")
                    private int createdBy;
                    @SerializedName("updated_by")
                    private int updatedBy;
                    @SerializedName("created_at")
                    private String createdAt;
                    @SerializedName("updated_at")
                    private String updatedAt;
                    @SerializedName("deleted_at")
                    private Object deletedAt;

                    public int getBrandId() {
                        return brandId;
                    }

                    public void setBrandId(int brandId) {
                        this.brandId = brandId;
                    }

                    public String getBrandCode() {
                        return brandCode;
                    }

                    public void setBrandCode(String brandCode) {
                        this.brandCode = brandCode;
                    }

                    public String getBrandName() {
                        return brandName;
                    }

                    public void setBrandName(String brandName) {
                        this.brandName = brandName;
                    }

                    public String getBrandDescription() {
                        return brandDescription;
                    }

                    public void setBrandDescription(String brandDescription) {
                        this.brandDescription = brandDescription;
                    }

                    public int getIsBrandActive() {
                        return isBrandActive;
                    }

                    public void setIsBrandActive(int isBrandActive) {
                        this.isBrandActive = isBrandActive;
                    }

                    public String getCreatedOn() {
                        return createdOn;
                    }

                    public void setCreatedOn(String createdOn) {
                        this.createdOn = createdOn;
                    }

                    public String getUpdatedOn() {
                        return updatedOn;
                    }

                    public void setUpdatedOn(String updatedOn) {
                        this.updatedOn = updatedOn;
                    }

                    public int getCreatedBy() {
                        return createdBy;
                    }

                    public void setCreatedBy(int createdBy) {
                        this.createdBy = createdBy;
                    }

                    public int getUpdatedBy() {
                        return updatedBy;
                    }

                    public void setUpdatedBy(int updatedBy) {
                        this.updatedBy = updatedBy;
                    }

                    public String getCreatedAt() {
                        return createdAt;
                    }

                    public void setCreatedAt(String createdAt) {
                        this.createdAt = createdAt;
                    }

                    public String getUpdatedAt() {
                        return updatedAt;
                    }

                    public void setUpdatedAt(String updatedAt) {
                        this.updatedAt = updatedAt;
                    }

                    public Object getDeletedAt() {
                        return deletedAt;
                    }

                    public void setDeletedAt(Object deletedAt) {
                        this.deletedAt = deletedAt;
                    }
                }

                public static class BrandType {
                    @SerializedName("type_id")
                    private int typeId;
                    @SerializedName("type_brand_id")
                    private int typeBrandId;
                    @SerializedName("type_code")
                    private String typeCode;
                    @SerializedName("type_name")
                    private String typeName;
                    @SerializedName("type_description")
                    private String typeDescription;
                    @SerializedName("is_type_active")
                    private int isTypeActive;
                    @SerializedName("created_on")
                    private String createdOn;
                    @SerializedName("updated_on")
                    private String updatedOn;
                    @SerializedName("created_by")
                    private int createdBy;
                    @SerializedName("updated_by")
                    private int updatedBy;
                    @SerializedName("created_at")
                    private String createdAt;
                    @SerializedName("updated_at")
                    private String updatedAt;
                    @SerializedName("deleted_at")
                    private Object deletedAt;

                    public int getTypeId() {
                        return typeId;
                    }

                    public void setTypeId(int typeId) {
                        this.typeId = typeId;
                    }

                    public int getTypeBrandId() {
                        return typeBrandId;
                    }

                    public void setTypeBrandId(int typeBrandId) {
                        this.typeBrandId = typeBrandId;
                    }

                    public String getTypeCode() {
                        return typeCode;
                    }

                    public void setTypeCode(String typeCode) {
                        this.typeCode = typeCode;
                    }

                    public String getTypeName() {
                        return typeName;
                    }

                    public void setTypeName(String typeName) {
                        this.typeName = typeName;
                    }

                    public String getTypeDescription() {
                        return typeDescription;
                    }

                    public void setTypeDescription(String typeDescription) {
                        this.typeDescription = typeDescription;
                    }

                    public int getIsTypeActive() {
                        return isTypeActive;
                    }

                    public void setIsTypeActive(int isTypeActive) {
                        this.isTypeActive = isTypeActive;
                    }

                    public String getCreatedOn() {
                        return createdOn;
                    }

                    public void setCreatedOn(String createdOn) {
                        this.createdOn = createdOn;
                    }

                    public String getUpdatedOn() {
                        return updatedOn;
                    }

                    public void setUpdatedOn(String updatedOn) {
                        this.updatedOn = updatedOn;
                    }

                    public int getCreatedBy() {
                        return createdBy;
                    }

                    public void setCreatedBy(int createdBy) {
                        this.createdBy = createdBy;
                    }

                    public int getUpdatedBy() {
                        return updatedBy;
                    }

                    public void setUpdatedBy(int updatedBy) {
                        this.updatedBy = updatedBy;
                    }

                    public String getCreatedAt() {
                        return createdAt;
                    }

                    public void setCreatedAt(String createdAt) {
                        this.createdAt = createdAt;
                    }

                    public String getUpdatedAt() {
                        return updatedAt;
                    }

                    public void setUpdatedAt(String updatedAt) {
                        this.updatedAt = updatedAt;
                    }

                    public Object getDeletedAt() {
                        return deletedAt;
                    }

                    public void setDeletedAt(Object deletedAt) {
                        this.deletedAt = deletedAt;
                    }
                }
            }
        }
    }
}
