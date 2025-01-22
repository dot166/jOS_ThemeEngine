package io.github.dot166.ThemeEngine;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.github.dot166.jLib.LIBAboutActivity;
import io.github.dot166.jLib.app.jAboutActivity;

public class TEAbout extends jAboutActivity {

    @NonNull
    @Override
    public List<Contributor> product() {
        return new ArrayList<>() {{
            add(new Contributor("._______166", LIBAboutActivity.Role.LeadDev, "https://avatars.githubusercontent.com/u/62702353", "https://github.com/dot166"));
            add(new Contributor("bh916", LIBAboutActivity.Role.Dev, "https://avatars.githubusercontent.com/u/138221251", "https://github.com/bh196"));
        }};
    }

    @NonNull
    @Override
    public List<jAboutActivity.Link> links() {
        return new ArrayList<>() {{
            add(new Link(io.github.dot166.jLib.R.drawable.ic_github, io.github.dot166.jLib.R.string.github, "https://github.com/dot166/jOS_ThemeEngine"));
        }};
    }
}
