package jp.ceed.android.mylapslogger.network.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ARAKI on 2017/04/30.
 * OAuthResponse
 */

public class OAuthResponse implements Serializable {


	/**
	 * access_token : rlnqiG0X4ebxKGm4nWFcaIgWWgh5pU3w9ZqaP8cuhwfXpVLgN1aISDZ_zTv7hi8myc2HwK8FOilnZ3v-2oEQZ_5wg8sDB7x6o34Zkg52igUc4cmwGAe7YsZUaKo6csdNcug3hcoVjrnTrqybbJeS_m6y-mT3bwffFXvKIYGQ1N9GhPUI0vh5mD7T4lZYnW7JjfCvxwAPRxaYMyi8ezT0uRiaqOgFOOGx3QzI-0De01asZaq8JHXy7qwxHblfcTHmh0C_jaV1d5lcT5KxXjRu_fMm5qIPSmw6U7Uk0s6bMn2eVKKdXtdQ8ro6as9cvVL8HXjhz7MfmBBfVKAeBQ5Ix-XFAtHbtmEJ-YCGscJJzCj2fz2pAjH008bDMu6V9O6KFcEY3YOGBloUx0yhCcWgyvNRYUDcffp0SRESgxx6eU-uqm1aKD5W5Wnj-amwrw2u
	 * token_type : bearer
	 * expires_in : 15767999
	 * userId : MYLAPS-GA-083b2fde5e25491a98a867fbb1135f6d
	 * .issued : Sun, 30 Apr 2017 14:40:20 GMT
	 * .expires : Mon, 30 Oct 2017 02:40:20 GMT
	 */

	@SerializedName("access_token")
	public String accessToken;

	@SerializedName("token_type")
	public String tokenType;

	@SerializedName("expires_in")
	public int expiresIn;

	@SerializedName("userId")
	public String userId;

	@SerializedName(".issued")
	public String issued;

	@SerializedName(".expires")
	public String expires;

}
