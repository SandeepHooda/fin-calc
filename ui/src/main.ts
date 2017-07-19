import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { AppModule } from './app/core/app.module';

platformBrowserDynamic().bootstrapModule(AppModule,[{provide:LocationStrategy , useClass:HashLocationStrategy}]);
