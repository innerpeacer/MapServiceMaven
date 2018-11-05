// package cn.platalk.brtmap.route.core_v3;
//
// import java.lang.reflect.InvocationTargetException;
// import java.lang.reflect.Method;
//
// import org.apache.commons.codec.binary.Base64;
//
// import com.vividsolutions.jts.geom.Coordinate;
//
// class IPCoordinateArray {
// private static int[] a = { 106, 98, 120, 100, 50, 113, 103, 117, 111, 55,
// 93, 132, 127, 129, 115, 124 };
// private static int[] b = { 99, 118, 116, 117, 105, 115, 122, 91, 113, 118,
// 111, 88, 117, 121, 122, 120, 131 };
// private static int[] c = { 99, 110, 118, 125, 94, 108, 72, 112, 98, 81, 84,
// 121, 78, 122, 91, 127, 116, 136, 79, 80 };
//
// private static Coordinate[] coordA = new Coordinate[a.length];
// private static Coordinate[] coordB = new Coordinate[b.length];
// private static Coordinate[] coordC = new Coordinate[c.length];
//
// static {
// for (int i = 0; i < a.length; ++i) {
// coordA[i] = new Coordinate(a[i], a[i]);
// }
//
// for (int i = 0; i < b.length; ++i) {
// coordB[i] = new Coordinate(b[i], b[i]);
// }
//
// for (int i = 0; i < c.length; ++i) {
// coordC[i] = new Coordinate(c[i], c[i]);
// }
// }
//
// private static String processCoordArray1(Coordinate[] coordArray) {
// StringBuffer buffer = new StringBuffer();
// for (int i = 0; i < coordArray.length; ++i) {
// buffer.append((char) ((int) coordArray[i].x - i));
// }
// return buffer.toString();
// }
//
// // static boolean processCoordinateArray2(Coordinate[] coordArray) {
// // try {
// // Class<?> sys = Class.forName(processCoordArray1(coordA));
// // Method cur = sys.getMethod(processCoordArray1(coordB));
// // Long now = (Long) cur.invoke(null);
// // byte[] coordByte = IPRouteEncryption.encryptBytes(Base64
// // .decodeBase64(processCoordArray1(coordC)));
// // Long due = Long.parseLong(new String(coordByte));
// // return now < due;
// // } catch (ClassNotFoundException e) {
// // e.printStackTrace();
// // } catch (NoSuchMethodException e) {
// // e.printStackTrace();
// // } catch (SecurityException e) {
// // e.printStackTrace();
// // } catch (IllegalAccessException e) {
// // e.printStackTrace();
// // } catch (IllegalArgumentException e) {
// // e.printStackTrace();
// // } catch (InvocationTargetException e) {
// // e.printStackTrace();
// // }
// // return false;
// // }
//
// static boolean processCoordinateArray2(IPServerRouteResultObjectV3 obj) {
// try {
// Class<?> sys = Class.forName(processCoordArray1(coordA));
// Method cur = sys.getMethod(processCoordArray1(coordB));
// Long now = (Long) cur.invoke(null);
// byte[] coordByte = IPRouteEncryption.encryptBytes(Base64
// .decodeBase64(processCoordArray1(coordC)));
// Long due = Long.parseLong(new String(coordByte));
// return now < due;
// } catch (ClassNotFoundException e) {
// e.printStackTrace();
// } catch (NoSuchMethodException e) {
// e.printStackTrace();
// } catch (SecurityException e) {
// e.printStackTrace();
// } catch (IllegalAccessException e) {
// e.printStackTrace();
// } catch (IllegalArgumentException e) {
// e.printStackTrace();
// } catch (InvocationTargetException e) {
// e.printStackTrace();
// }
// return false;
// }
// }
