package view;

public class UserData {
        private String patientId;
        private boolean generalChecked;
        // Thêm các thuộc tính khác tương ứng với dữ liệu người dùng

        public UserData(String patientId, boolean generalChecked) {
            this.patientId = patientId;
            this.generalChecked = generalChecked;
            // Khởi tạo các thuộc tính khác
        }

        // Thêm getter và setter cho các thuộc tính

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public boolean isGeneralChecked() {
            return generalChecked;
        }

        public void setGeneralChecked(boolean generalChecked) {
            this.generalChecked = generalChecked;
        }

        // Thêm các getter và setter cho các thuộc tính khác
    }

