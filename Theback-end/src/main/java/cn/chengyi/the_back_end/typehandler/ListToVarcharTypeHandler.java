package cn.chengyi.the_back_end.typehandler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * <img src="http://blog.GnaixEuy.cn/wp-content/uploads/2021/08/bug.jpeg"/>
 * <p>mybatis list转字符串转换器</p>
 * @author GnaixEuy
 * @date 2021/12/21
 * @see <a href='https://github.com/GnaixEuy'> GnaixEuy的GitHub </a>
 */

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
@Component
public class ListToVarcharTypeHandler  implements TypeHandler<List<String>> {
	@Override
	public void setParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
		// 遍历List类型的入参，拼装为String类型，使用Statement对象插入数据库
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < strings.size(); j++) {
			if (j == strings.size() - 1) {
				sb.append(strings.get(j));
			} else {
				sb.append(strings.get(j)).append("@");
			}
		}
		preparedStatement.setString(i, sb.toString());
	}

	@Override
	public List<String> getResult(ResultSet resultSet, String s) throws SQLException {
		// 获取String类型的结果，使用","分割为List后返回
		String resultString = resultSet.getString(s);
		if (StringUtils.isNotEmpty(resultString)) {
			return Arrays.asList(resultString.split("@"));
		}
		return null;
	}

	@Override
	public List<String> getResult(ResultSet resultSet, int i) throws SQLException {
		// 获取String类型的结果，使用","分割为List后返回
		String resultString = resultSet.getString(i);
		if (StringUtils.isNotEmpty(resultString)) {
			return Arrays.asList(resultString.split("@"));
		}
		return null;
	}

	@Override
	public List<String> getResult(CallableStatement callableStatement, int i) throws SQLException {
		// 获取String类型的结果，使用","分割为List后返回
		String resultString = callableStatement.getString(i);
		if (StringUtils.isNotEmpty(resultString)) {
			return Arrays.asList(resultString.split("@"));
		}
		return null;
	}
}
