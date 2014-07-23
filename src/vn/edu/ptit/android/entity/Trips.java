package vn.edu.ptit.android.entity;

import java.io.Serializable;

import org.json.JSONObject;

public class Trips implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	int taiXeid;
	String loaiXe;
	String thoiGian;
	int soGhe;
	int soGheCon;
	String loTrinh;
	String note;

	public Trips() {
		// TODO Auto-generated constructor stub
	}

	public Trips(int id, int taiXeid, String loaiXe, String thoiGian,
			int soGhe, int soGheCon, String loTrinh, String note) {
		super();
		this.id = id;
		this.taiXeid = taiXeid;
		this.loaiXe = loaiXe;
		this.thoiGian = thoiGian;
		this.soGhe = soGhe;
		this.soGheCon = soGheCon;
		this.loTrinh = loTrinh;
		this.note = note;
	}

	public Trips(JSONObject e) throws Exception {
		this.taiXeid = e.getInt("idnguoidung");
		this.loaiXe = e.getString("loaixe");
		this.thoiGian = e.getString("thoigian");
		this.soGhe = e.getInt("soghe");
		this.loTrinh = e.getString("lotrinh");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTaiXeid() {
		return taiXeid;
	}

	public void setTaiXeid(int taiXeid) {
		this.taiXeid = taiXeid;
	}

	public String getLoaiXe() {
		return loaiXe;
	}

	public void setLoaiXe(String loaiXe) {
		this.loaiXe = loaiXe;
	}

	public String getThoiGian() {
		return thoiGian;
	}

	public void setThoiGian(String thoiGian) {
		this.thoiGian = thoiGian;
	}

	public int getSoGhe() {
		return soGhe;
	}

	public void setSoGhe(int soGhe) {
		this.soGhe = soGhe;
	}

	public int getSoGheCon() {
		return soGheCon;
	}

	public void setSoGheCon(int soGheCon) {
		this.soGheCon = soGheCon;
	}

	public String getLoTrinh() {
		return loTrinh;
	}

	public void setLoTrinh(String loTrinh) {
		this.loTrinh = loTrinh;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
