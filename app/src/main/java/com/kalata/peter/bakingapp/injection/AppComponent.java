package com.kalata.peter.bakingapp.injection;

import com.kalata.peter.bakingapp.ui.main.MainViewModel;
import com.kalata.peter.bakingapp.widget.BakingAppWidgetProvider;
import com.kalata.peter.bakingapp.widget.WidgetActivity;
import com.kalata.peter.bakingapp.widget.WidgetViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {

    void inject(MainViewModel viewModel);
    void inject(WidgetViewModel viewModel);

}