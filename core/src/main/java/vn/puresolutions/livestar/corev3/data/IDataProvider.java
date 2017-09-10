package vn.puresolutions.livestar.corev3.data;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/29/2015
 */
public interface IDataProvider<T> {
    public void save(T data) throws Exception;
    public T load(Class clzz) throws Exception;
}
