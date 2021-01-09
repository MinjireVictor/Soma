package com.mackvel.soma.Fragments;

import com.mackvel.soma.Notification.MyResponse;
import com.mackvel.soma.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAJefxOSE:APA91bEN8_gxf36AThMx-60XiJuLbdYJqj-CO3IOsQS00AZPn6G5inf5AJGsav9gyhGJyqzD0e8r6uPWjSmjq3vACdi0O1-yawauqA-dKp7BojIySU6ok6DPpcazhBSuRAXKElRx8Lli"

            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);


}
